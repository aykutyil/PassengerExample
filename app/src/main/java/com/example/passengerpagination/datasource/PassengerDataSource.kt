package com.example.passengerpagination.datasource

import androidx.paging.PageKeyedDataSource
import com.example.passengerpagination.model.Passenger
import com.example.passengerpagination.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PassengerDataSource : PageKeyedDataSource<Int, Passenger.Data>() {

    private val disposable = CompositeDisposable()

    companion object {
        const val PAGE_SIZE = 20
        const val FIRST_PAGE = 2
        var FINAL_PAGE = 0
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Passenger.Data>
    ) {
        val call = ApiService().getData(FIRST_PAGE)
        disposable.add(
            call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Passenger>() {
                    override fun onSuccess(t: Passenger) {
                        t.let {
                            FINAL_PAGE = it.totalPages
                            it.data?.let { passengerList ->
                                callback.onResult(
                                    passengerList,
                                    null,
                                    FIRST_PAGE + 1
                                )
                            }
                            disposable.clear()
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Passenger.Data>) {
        val call = ApiService().getData(params.key)
        disposable.add(
            call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Passenger>() {
                    override fun onSuccess(t: Passenger) {
                        val key = params.key + 1

                        t.let {
                            it.data?.let { passengerList ->
                                callback.onResult(passengerList, key)
                            }
                            disposable.clear()
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Passenger.Data>) {
        val call = ApiService().getData(params.key)
        disposable.add(
            call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Passenger>() {
                    override fun onSuccess(t: Passenger) {
                        val key = if (params.key < FINAL_PAGE) params.key + 1 else 0
                        t.let { passenger ->
                            passenger.data?.let { passengerList ->
                                callback.onResult(passengerList, key)
                            }
                            disposable.clear()
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }
}