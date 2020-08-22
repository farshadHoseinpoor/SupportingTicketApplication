package com.example.ticketmodule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmodule.databinding.BindingLayoutTicketDetail
import com.example.ticketmodule.model.TicketDetail


class TicketDetailAdapter(var ticketDetail: ArrayList<TicketDetail>, val userId: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding =
            BindingLayoutTicketDetail.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketDetailViewHolder(binding)
    }

    override fun getItemCount(): Int = ticketDetail.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TicketDetailViewHolder -> {
                holder.bind(ticketDetail[position],userId)
            }
        }
    }

     class TicketDetailViewHolder constructor(val binding: BindingLayoutTicketDetail) : RecyclerView.ViewHolder(binding.root) {

        val txtMessage:TextView=binding.txtMessage


        fun bind(ticketDetail: TicketDetail, userId: Int) {
            binding.ticketDetail = ticketDetail


            val cs = ConstraintSet()
            cs.clone(binding.ticketConstraint)
            if (ticketDetail.user_id == userId){
                cs.setHorizontalBias(binding.txtMessage.id, 1.toFloat())
            }else{
                cs.setHorizontalBias(binding.txtMessage.id, 0.toFloat())
            }
            cs.applyTo(binding.ticketConstraint)
        }
    }
}

