package com.example.ticketmodule.ui.activity



import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticketmodule.R
import com.example.ticketmodule.viewModel.TicketDetailViewModel
import com.example.ticketmodule.adapter.TicketDetailAdapter
import com.example.ticketmodule.api.ApiService
import com.example.ticketmodule.databinding.BindingActivityTicketDetail
import com.example.ticketmodule.tools.di.AppModule
import com.example.ticketmodule.tools.di.DaggerAppComponent
import kotlinx.android.synthetic.main.activity_ticket_detail.*
import javax.inject.Inject

class TicketDetailActivity : BaseActivity<BindingActivityTicketDetail,TicketDetailViewModel>() {

    companion object{
       lateinit var messageContent:String
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var apiService: ApiService

    private lateinit var adapter: TicketDetailAdapter


    override fun inject() {

        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)

    }

    override fun onStart() {
        super.onStart()

        val id = intent.getIntExtra("id", 0)


        val handler = Handler()
        val task: Runnable = object : Runnable {
            override fun run() {
                viewModel.getTicketDetails(id)
                handler.postDelayed(this, 100)
            }
        }
        handler.removeCallbacks(task)
        handler.post(task)
    }

    override fun getClassViewModel(): Class<TicketDetailViewModel> = TicketDetailViewModel::class.java

    override fun getActivityLayoutId(): Int = R.layout.activity_ticket_detail

    override fun getViewModelFactory(): ViewModelProvider.Factory = TicketDetailViewModel.Factory(sharedPreferences, apiService)



    override fun initUi() {
        var isInit = false


        val id = intent.getIntExtra("id", 0)
        val userId= sharedPreferences.getInt("user_id",0)




            viewModel.ticketDetailsLiveData.observe(this, Observer {

                if (!isInit){
                    messageList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true)
                    adapter = TicketDetailAdapter(it,userId)
                    messageList.adapter = adapter
                    isInit=true
                }else{
                    if (adapter.ticketDetail.size < it.size){
                        adapter.ticketDetail=it
                        adapter.notifyDataSetChanged()
                    }
                }



            })




        viewModel.isHaveErrorLiveData.observe(this, Observer {
            if(it){
                val builder= AlertDialog.Builder(this)
                builder.setMessage("خطا در دریافت اطلاعات")
                builder.setCancelable(true)
                builder.show()
            }
        })
        binding.editTextMessage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                messageContent=p0.toString()

            }

        })
        binding.btnSend.setOnClickListener {
            if (editTextMessage.text.isNotEmpty()){
                viewModel.createTicketDetail(userId,id, messageContent)

                resetInput()
            }

        }
    }
    private fun resetInput() {
        // Clean text box
        editTextMessage.text.clear()

        // Hide keyboard
        val inputManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

}