package com.example.omarapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.omarapplication.OmarApplication
import com.example.omarapplication.R
import com.example.omarapplication.data.entities.Customer
import com.example.omarapplication.databinding.FragmentDetailBinding
import com.example.omarapplication.viewmodel.AppViewModel
import com.example.omarapplication.viewmodel.AppViewModelFactory

class DetailFragment : Fragment() {

    private val navigationArgs: DetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var customer: Customer

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
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.newPaymentButton.setOnClickListener { navigateAddPayment(customer.customerId) }
        binding.paymentsButton.setOnClickListener { navigatePaymentList() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.customerId
        viewModel.retrieveCustomer(id).observe(this.viewLifecycleOwner) { selectedCustomer ->
            customer = selectedCustomer
            bind(customer)
        }
    }

    private fun navigateAddPayment(id:Int) {
        val action = DetailFragmentDirections.actionDetailFragmentToAddPaymentFragment(id)
        findNavController().navigate(action)
    }

    private fun navigatePaymentList() {
        val action = DetailFragmentDirections.actionDetailFragmentToPaymentFragment()
        findNavController().navigate(action)
    }

    private fun bind(customer: Customer) {
        binding.apply {
            idDet.text = customer.customerId.toString()
            nameDet.text = customer.customerName
            middleNameDet.text = customer.middleName
            lastNameDet.text = customer.lastName
            secondLastNameDet.text = customer.secondLastName
            dateDet.text = customer.birthdate
            genderDet.text = viewModel.getGenderLabel(customer.gender)
            editButton.setOnClickListener { editCustomer() }
        }
    }

    fun editCustomer() {
        val action = DetailFragmentDirections.actionDetailFragmentToAddCustomerFragment(
            getString(R.string.editFragment_label),
            customer.customerId
        )
        this.findNavController().navigate(action)
    }


}