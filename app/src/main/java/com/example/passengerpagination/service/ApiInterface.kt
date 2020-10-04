package com.example.passengerpagination.service

import com.example.passengerpagination.model.Passenger
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("passenger?")
    fun getPassengers(@Query("page") page:Int,@Query("size") size:Int ):Single<Passenger>
}