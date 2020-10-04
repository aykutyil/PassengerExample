package com.example.passengerpagination.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passengerpagination.adapter.PassengerAdapter
import com.example.passengerpagination.databinding.FragmentPassengerBinding
import com.example.passengerpagination.viewmodel.PassengerFragmentViewModel
import kotlinx.android.synthetic.main.fragment_passenger.*

class PassengerFragment : Fragment() {

    private lateinit var binding: FragmentPassengerBinding

    private val adapter = PassengerAdapter()

    private lateinit var viewModel: PassengerFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentPassengerBinding.inflate(inflater, container, false)
        .apply {
        binding = this
        fragment = this@PassengerFragment
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PassengerFragmentViewModel::class.java)

        passenger_recyclerview.layoutManager = LinearLayoutManager(context)
        passenger_recyclerview.adapter = adapter

        begin()

    }

     fun begin() {
        context?.let {
            viewModel.isNetworkConneted(it)
            networkCheck()
        }
    }

    private fun networkCheck() {
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            if (it) {
                networkMessage.visibility = View.INVISIBLE
                tvTryAgainConnect.visibility = View.INVISIBLE
                viewModel.setAdapter()
                setPassenger()
            } else{
                networkMessage.visibility = View.VISIBLE
                tvTryAgainConnect.visibility = View.VISIBLE

            }
        })
    }

    private fun setPassenger() {
        viewModel.stateProgressBar.observe(viewLifecycleOwner, Observer {
            if (it){
                pageLoading.visibility = View.VISIBLE

            }else
                pageLoading.visibility = View.INVISIBLE
        })

        viewModel.passengerPagedList.observe(viewLifecycleOwner, Observer {
                passenger_recyclerview.addOnScrollListener(viewModel.prOnScrollListener())
                adapter.submitList(it)
        })
    }



}