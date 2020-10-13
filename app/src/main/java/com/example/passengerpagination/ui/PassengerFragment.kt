package com.example.passengerpagination.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passengerpagination.adapter.PassengerAdapter
import com.example.passengerpagination.databinding.FragmentPassengerBinding
import com.example.passengerpagination.viewmodel.PassengerFragmentViewModel

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
        binding.passengerRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.passengerRecyclerview.adapter = adapter

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
                binding.networkMessage.visibility = View.INVISIBLE
                binding.tvTryAgainConnect.visibility = View.INVISIBLE
                viewModel.setAdapter()
                setPassenger()
            } else {
                binding.networkMessage.visibility = View.VISIBLE
                binding.tvTryAgainConnect.visibility = View.VISIBLE

            }
        })
    }

    private fun setPassenger() {
        try {

            viewModel.stateProgressBar.observe(viewLifecycleOwner, Observer {
                if (it) {
                    binding.pageLoading.visibility = View.VISIBLE
                }else
                    binding.pageLoading.visibility = View.GONE
            })

            viewModel.passengerPagedList.observe(viewLifecycleOwner, Observer {
                binding.passengerRecyclerview.addOnScrollListener(viewModel.prOnScrollListener())
                adapter.submitList(it)
            })
        } catch (e: Exception) {
            Toast.makeText(context, "Bir hata oluştu.", Toast.LENGTH_LONG).show()
            Log.e("Error", e.message.toString())
            binding.networkMessage.visibility = View.VISIBLE
            binding.tvTryAgainConnect.visibility = View.VISIBLE
        }

    }
}