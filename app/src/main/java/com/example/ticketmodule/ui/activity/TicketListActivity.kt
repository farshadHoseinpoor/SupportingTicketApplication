package com.example.ticketmodule.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.ticketmodule.R
import com.example.ticketmodule.ViewModel.TicketHeadViewModel
import com.example.ticketmodule.adapter.TicketHeadAdapter
import com.example.ticketmodule.api.ApiService
import com.example.ticketmodule.databinding.BindingActivityTicketList
import com.example.ticketmodule.tools.di.AppModule
import com.example.ticketmodule.tools.di.DaggerAppComponent
import com.example.ticketmodule.tools.utils.UpdateWorker
import kotlinx.android.synthetic.main.activity_ticket_list.*
import kotlinx.android.synthetic.main.layout_new_ticket_dialog.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class TicketListActivity : BaseActivity<BindingActivityTicketList, TicketHeadViewModel>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var apiService: ApiService

    lateinit var ticketHeadAdapter: TicketHeadAdapter


    override fun inject() {
        DaggerAppComponent.builder()
            .appModule(AppModule(this@TicketListActivity))
            .build()
            .inject(this)
    }

    override fun onStart() {
        super.onStart()


        val handler = Handler()
        val task: Runnable = object : Runnable {
            override fun run() {
                viewModel.getTicketHeads(1)
                handler.postDelayed(this, 2000)
            }
        }
        handler.removeCallbacks(task)
        handler.post(task)
    }

    override fun getClassViewModel(): Class<TicketHeadViewModel> = TicketHeadViewModel::class.java

    override fun getActivityLayoutId(): Int = R.layout.activity_ticket_list

    override fun getViewModelFactory(): ViewModelProvider.Factory =
        TicketHeadViewModel.Factory(sharedPreferences, apiService)

    fun backgroundUpdateWorker() {
        val updateWorker = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(5, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(updateWorker)

    }

    override fun initUi() {
        var isInit = false
        sharedPreferences.edit().putInt("user_id", 1).apply()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.getTicketHeads(1)
        viewModel.ticketHeadsLiveData.observe(this, Observer {


            if (!isInit) {
                ticketHeadAdapter = TicketHeadAdapter(it, onItemClickCallBack = {

                    val intent = Intent(this, TicketDetailActivity::class.java)
                    intent.putExtra("id", it)
                    startActivity(intent)

                })
                recyclerView.adapter = ticketHeadAdapter
                isInit = true
            } else {
                if (ticketHeadAdapter.ticketHead.size < it.size) {
                    ticketHeadAdapter.ticketHead = it
                    ticketHeadAdapter.notifyDataSetChanged()
                }
            }
        })

        viewModel.isHaveErrorLiveData.observe(this, Observer {
            if (it) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("خطا در دریافت اطلاعات")
                    .setCancelable(true)
                    .create()
                builder.show()

            }
        })
        binding.fab.setOnClickListener {
            val dialogView =
                LayoutInflater.from(this).inflate(R.layout.layout_new_ticket_dialog, null)
            val dialogBuilder = AlertDialog.Builder(this).setView(dialogView).setTitle("ایجاد تیکت")
                .setCancelable(false)
            val alertDialog = dialogBuilder.show()

            dialogView.btn_createTicket.setOnClickListener {
                val subject = dialogView.editText_subject.text.toString()
                val body = dialogView.editText_body.text.toString()
                if (dialogView.editText_subject.text.isNotEmpty() && dialogView.editText_body.text.isNotEmpty()) {
                    viewModel.createNewTicket(1, subject, body)
                    alertDialog.dismiss()
                } else {
                    Toast.makeText(this, R.string.txt_toast_message, Toast.LENGTH_SHORT).show()
                }

            }
            dialogView.btn_cancelCreateTicket.setOnClickListener {
                alertDialog.dismiss()
            }

        }


    }

}
