package com.example.ticketmodule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmodule.R
import com.example.ticketmodule.databinding.BindingLayoutTicketHead
import com.example.ticketmodule.model.TicketHead

class TicketHeadAdapter(var ticketHead: ArrayList<TicketHead>, val onItemClickCallBack: (id: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding =
            BindingLayoutTicketHead.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketHeadViewHolder(binding, onItemClickCallBack)
    }

    override fun getItemCount(): Int = ticketHead.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TicketHeadViewHolder -> {
                holder.bind(ticketHead[position])
            }
        }
    }

    class TicketHeadViewHolder constructor(val binding: BindingLayoutTicketHead, val onItemClickCallBack: (id: Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ticketHead: TicketHead) {
            binding.ticketHead = ticketHead
            binding.cardView.setOnClickListener {
                var id = ticketHead.id
                onItemClickCallBack(id)
            }

        }
    }
}
