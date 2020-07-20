package com.example.ticketmodule.model



data class TicketDetail(val id:Int,
                       val ticket_id:Int,
                       val user_id:Int,
                       val body:String,
                       val file_path:String?,
                       val created_at:Long,
                       val updated_at:String)
