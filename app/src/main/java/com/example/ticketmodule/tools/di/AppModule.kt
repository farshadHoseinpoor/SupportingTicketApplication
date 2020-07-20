package com.example.ticketmodule.tools.di

import android.content.Context
import android.content.SharedPreferences
import com.example.ticketmodule.api.ApiService
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("ticket", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideApiService():ApiService{
        return ApiService.create()
    }
}