package com.example.passengerpagination.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.passengerpagination.model.Passenger

class PassengerDataSourceFactory:DataSource.Factory<Int,Passenger.Data>() {

    val passengerLiveDataSource = MutableLiveData<PassengerDataSource>()

    override fun create(): DataSource<Int, Passenger.Data> {
        val passengerDataSource = PassengerDataSource()
        passengerLiveDataSource.postValue(passengerDataSource)

        return passengerDataSource
    }
}