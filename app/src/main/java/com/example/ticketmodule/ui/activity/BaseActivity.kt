package com.example.ticketmodule.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.ticketmodule.ViewModel.BaseViewModel


abstract class BaseActivity<E : ViewDataBinding, T : BaseViewModel> : AppCompatActivity(),
    LifecycleOwner {

    lateinit var binding : E
    lateinit var viewModel: T

    abstract fun inject()

    abstract fun getClassViewModel(): Class<T>

    abstract fun getActivityLayoutId() : Int

    abstract fun getViewModelFactory() : ViewModelProvider.Factory


    abstract fun initUi()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject()

        binding = DataBindingUtil.setContentView(this,getActivityLayoutId())

        viewModel = ViewModelProvider(this,getViewModelFactory()).get(getClassViewModel())

        lifecycle.addObserver(viewModel)

        initUi()

        inject()

    }



}

