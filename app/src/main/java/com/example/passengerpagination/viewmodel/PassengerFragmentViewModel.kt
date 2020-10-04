package com.example.passengerpagination.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passengerpagination.datasource.PassengerDataSource
import com.example.passengerpagination.datasource.PassengerDataSourceFactory
import com.example.passengerpagination.model.Passenger

class PassengerFragmentViewModel:ViewModel() {

    val passengerPagedList: LiveData<PagedList<Passenger.Data>>

    var networkState = MutableLiveData<Boolean>()

    var stateProgressBar = MutableLiveData<Boolean>()

    private val itemDataSourceFactory = PassengerDataSourceFactory()
    private val liveDataSource: LiveData<PassengerDataSource>

    init {

        liveDataSource = itemDataSourceFactory.passengerLiveDataSource

        val config = PagedList.Config
            .Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PassengerDataSource.PAGE_SIZE)
            .build()

        passengerPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()

    }

    fun isNetworkConneted(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork
        val actNw = connectivityManager.getNetworkCapabilities(nw)
        if (actNw != null) {
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> networkState.value = true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> networkState.value =
                    true
                else -> networkState.value = false
            }
        }
    }

    fun prOnScrollListener() =
        object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                stateProgressBar.value = isLastItemDisplaying(recyclerView)
            }
        }

    fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
        return if (recyclerView.adapter?.itemCount != 0) {
            val lastVisibleItemPosition =
                ((recyclerView.layoutManager) as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

            lastVisibleItemPosition != RecyclerView.NO_POSITION &&
                    lastVisibleItemPosition == recyclerView.adapter?.itemCount?.minus(1)
        } else
            false
    }
}