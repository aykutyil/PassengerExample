package com.example.passengerpagination.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Passenger {

    @SerializedName("totalPassengers")
    var totalPassengers = 0

    @SerializedName("totalPages")
    var totalPages = 0

    @SerializedName("data")
    var data: List<Data>? = null


     class Data {
         @SerializedName("_id")
         val _id: String ?=null

         @SerializedName("name")
         val name: String ?=null

         @SerializedName("trips")
         val trips: Int = 0

         @SerializedName("airline")
         val airline: Airline? = null

         @SerializedName("__v")
         val __v: Int = 0
     }

    class Airline {
        @SerializedName("id")
        var ıd = 0

        @SerializedName("name")
        var name: String? = null

        @SerializedName("country")
        var country: String? = null

        @SerializedName("logo")
        var logo: String? = null

        @SerializedName("slogan")
        var slogan: String? = null

        @SerializedName("head_quaters")
        var head_quaters: String? = null

        @SerializedName("website")
        var website: String? = null

        @SerializedName("established")
        var established: String? = null

    }

    /*@SerializedName("totalPassengers")
    var totalPassengers = 0

    @SerializedName("totalPages")
    var totalPages = 0

    @SerializedName("data")
    var data: List<Data>? = null

     class Data(
        @Expose
        @SerializedName("_id")
        val _id: String,
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("trips")
        val trips: Int,
        @Expose
        @SerializedName("airline")
        val airline: List<Airline>,
        @Expose
        @SerializedName("__v")
        val __v: Int
    )

     class Airline(
        @Expose
        @SerializedName("id")
        val id: Int,
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("country")
        val country: String,
        @Expose
        @SerializedName("logo")
        val logo: String,
        @Expose
        @SerializedName("slogan")
        val slogan: String,
        @Expose
        @SerializedName("head_quaters")
        val head_quaters: String,
        @Expose
        @SerializedName("website")
        val website: String,
        @Expose
        @SerializedName("established")
        val established: String
    )*/


}




