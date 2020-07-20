package com.example.ticketmodule.ViewModel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ticketmodule.api.ApiService
import com.example.ticketmodule.api.getTicketHeadApi
import com.example.ticketmodule.api.createNewTicketApi
import com.example.ticketmodule.model.TicketHead

class TicketHeadViewModel(val sharedPreferences: SharedPreferences,val apiService: ApiService) :BaseViewModel(){

    class Factory(val sharedPreferences: SharedPreferences, val apiService: ApiService) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TicketHeadViewModel(sharedPreferences, apiService) as T
        }
    }

    val ticketHeadsLiveData =MutableLiveData<ArrayList<TicketHead>>()

   val isHaveErrorLiveData=MutableLiveData<Boolean>()



    fun getTicketHeads(userId: Int){
        statusLiveData.postValue(true)
        isHaveErrorLiveData.postValue(false)
        getTicketHeadApi(userId,apiService,onSuccess = {

            if (it!=null){
                ticketHeadsLiveData.postValue(it)
            }else isHaveErrorLiveData.postValue(true)

        })

    }
    fun createNewTicket(userId:Int, subject:String, body:String){
        isHaveErrorLiveData.postValue(false)
        createNewTicketApi(userId,subject,body,apiService,onSuccess = {

            if (it.isNotEmpty()){


            }

        })

    }
}