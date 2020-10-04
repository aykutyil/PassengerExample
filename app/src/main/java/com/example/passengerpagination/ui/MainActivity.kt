package com.example.passengerpagination.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.passengerpagination.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* val adapter = PassengerAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemViewModel = ViewModelProviders.of(this)
            .get(PassengerFragmentViewModel::class.java)

        itemViewModel.passengerPagedList.observe(this, Observer {
            adapter.submitList(it)
        })

        recyclerView.adapter = adapter*/
    }
}