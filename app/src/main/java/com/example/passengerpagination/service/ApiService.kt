package com.example.passengerpagination.service

import com.example.passengerpagination.model.Passenger
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val BASE_URL = "https://api.instantwebtools.net/v1/"

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logger)

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttp.build())
        .build()
        .create(ApiInterface::class.java)

    fun getData(serviceType : Int):Single<Passenger>{
        return api.getPassengers(serviceType,20)
    }
}