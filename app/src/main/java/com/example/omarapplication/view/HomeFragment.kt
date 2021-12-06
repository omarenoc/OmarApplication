package com.example.omarapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.omarapplication.R
import com.example.omarapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.customerBtn.setOnClickListener { navigateCustomerFragment() }
        binding.paymentBtn.setOnClickListener { navigatePaymentFragment() }
        binding.newCustomerBtn.setOnClickListener { navigateAddCustomerFragment() }
        binding.newPaymentBtn.setOnClickListener { navigateNewPaymentFragment() }
        return binding.root
    }

    private fun navigateNewPaymentFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToAddPaymentFragment()
        findNavController().navigate(action)
    }

    private fun navigateAddCustomerFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToAddCustomerFragment(
            getString(R.string.addFragment_label)
        )
        findNavController().navigate(action)
    }

    private fun navigatePaymentFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToPaymentFragment()
        findNavController().navigate(action)
    }

    private fun navigateCustomerFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToCustomerFragment()
        findNavController().navigate(action)
    }
}