package com.example.ticketmodule.viewModel


import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ticketmodule.api.ApiService
import com.example.ticketmodule.api.createTicketDetailApi
import com.example.ticketmodule.api.getTicketDetailApi
import com.example.ticketmodule.model.TicketDetail

class TicketDetailViewModel(val sharedPreferences: SharedPreferences,val apiService: ApiService) :BaseViewModel(){

    class Factory(val sharedPreferences: SharedPreferences,val apiService: ApiService):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TicketDetailViewModel(sharedPreferences,apiService) as T
        }
    }

    val ticketDetailsLiveData=MutableLiveData<ArrayList<TicketDetail>>()


    val isHaveErrorLiveData=MutableLiveData<Boolean>()

    var ticketId = 0

    fun getTicketDetails(id : Int){
        ticketId=id
        statusLiveData.postValue(true)
        isHaveErrorLiveData.postValue(false)
        getTicketDetailApi(apiService =  apiService,ticket_id = id,onSuccess = {
            if (it!=null){
               ticketDetailsLiveData.postValue(it)
            }else isHaveErrorLiveData.postValue(true)

        })

    }

    fun createTicketDetail(userId:Int,ticketId:Int,body:String){
        isHaveErrorLiveData.postValue(false)
        createTicketDetailApi(userId,ticketId,body,apiService,onSuccess = {

        })
    }
}