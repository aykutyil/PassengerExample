package com.example.passengerpagination.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.passengerpagination.R
import com.example.passengerpagination.databinding.ItemPassengerBinding
import com.example.passengerpagination.model.Passenger
import kotlinx.android.synthetic.main.item_passenger.view.*

class PassengerAdapter : PagedListAdapter<Passenger.Data,PassengerAdapter.PassengerViewHolder>(
    PASSENGER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemPassengerBinding>(
            inflater,
            R.layout.item_passenger,
            parent,
            false
        )

        return PassengerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        val passenger = getItem(position)
        passenger?.let {instancePassenger->
            holder.view.passenger = instancePassenger

            holder.view.airlinesLink.setOnClickListener {
                val link = Uri.parse("http://"+instancePassenger.airline?.website)
                val launchBrowser = Intent(Intent.ACTION_VIEW,link)
                it.context.startActivity(launchBrowser)
            }
        }
    }

    class PassengerViewHolder(var view:ItemPassengerBinding):RecyclerView.ViewHolder(view.root)
    
    companion object{
        private val PASSENGER_COMPARATOR = object : DiffUtil.ItemCallback<Passenger.Data>() {
            override fun areItemsTheSame(oldItem: Passenger.Data, newItem: Passenger.Data): Boolean =
                oldItem._id == newItem._id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Passenger.Data, newItem: Passenger.Data): Boolean =
                newItem == oldItem
        }
    }
}