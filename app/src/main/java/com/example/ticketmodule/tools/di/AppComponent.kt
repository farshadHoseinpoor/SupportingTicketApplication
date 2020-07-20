package com.example.ticketmodule.tools.di


import com.example.ticketmodule.ui.activity.TicketDetailActivity
import com.example.ticketmodule.ui.activity.TicketListActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(target:TicketListActivity)

    fun inject(target:TicketDetailActivity)
}