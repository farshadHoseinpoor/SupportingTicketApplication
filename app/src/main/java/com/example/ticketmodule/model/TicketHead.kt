package com.example.ticketmodule.model

data class TicketHead(val id:Int=0,
                      val user_id:Int=0,
                      val status_id:Int=0,
                      val subject:String="",
                      val file_Path:String?="",
                      val created_at:Long=0,
                      val updated_at:Long=0
)