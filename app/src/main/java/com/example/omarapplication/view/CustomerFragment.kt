package com.example.omarapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omarapplication.adapters.CustomerRecyclerViewAdapter
import com.example.omarapplication.OmarApplication
import com.example.omarapplication.R
import com.example.omarapplication.databinding.FragmentCustomerItemListBinding
import com.example.omarapplication.viewmodel.AppViewModel
import com.example.omarapplication.viewmodel.AppViewModelFactory


class CustomerFragment : Fragment() {
    private var _binding: FragmentCustomerItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppViewModel by activityViewModels {
        AppViewModelFactory(
            (activity?.application as OmarApplication).database.customerDao(),
            (activity?.application as OmarApplication).database.paymentDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CustomerRecyclerViewAdapter {
            val action = CustomerFragmentDirections.actionCustomerFragmentToDetailFragment(it.customerId)
            this.findNavController().navigate(action)
        }
        binding.list.adapter = adapter
        viewModel.customersList.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        binding.list.layoutManager = LinearLayoutManager(this.context)
        //binding.nameHeader.setOnClickListener { navigateDetailCustomer() }
        binding.addFab.setOnClickListener { navigateAddCustomer() }
    }


    private fun navigateAddCustomer() {
        val action = CustomerFragmentDirections.actionCustomerFragmentToAddCustomerFragment(
            getString(R.string.addFragment_label)
        )
        findNavController().navigate(action)
    }

}