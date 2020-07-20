package com.example.ticketmodule.tools.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.ticketmodule.model.TicketDetail
import com.example.ticketmodule.ui.activity.TicketListActivity


class UpdateWorker (context: Context,workerParameters: WorkerParameters) : Worker(context,workerParameters){

    override fun doWork(): Result {
       val ticketListActivity=TicketListActivity()
       ticketListActivity.initUi()

        return Result.success()
    }


}
