package com.example.ticketmodule.viewModel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    val statusLiveData=MutableLiveData<Boolean>()


}
