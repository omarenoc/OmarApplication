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
import com.example.omarapplication.data.entities.Payment
import com.example.omarapplication.databinding.FragmentAddPaymentBinding
import com.example.omarapplication.viewmodel.AppViewModel
import com.example.omarapplication.viewmodel.AppViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AddPaymentFragment : Fragment() {
    private val navigationArgs: AddPaymentFragmentArgs by navArgs()

    private var _binding: FragmentAddPaymentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppViewModel by activityViewModels {
        AppViewModelFactory(
            (activity?.application as OmarApplication).database.customerDao(),
            (activity?.application as OmarApplication).database.paymentDao()
        )
    }

    lateinit var payment: Payment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.custPid
        if (id > 0) {
            binding.idCustomerPay.setText(id.toString())
            binding.idCustomerPay.isEnabled = false
        }
        binding.savePayment.setOnClickListener { saveAndNavigateHome() }
    }


    private fun saveAndNavigateHome() {
        addNewPayment()
        val action = AddPaymentFragmentDirections.actionAddPaymentFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun isPaymentValid(): Boolean {
        return  viewModel.isPaymentValid(
            binding.idCustomerPay.text.toString().toInt(),
            getPaymentDate(),
            getPaymentAmount()
        )
    }

    private fun addNewPayment() {
        if (isPaymentValid()) {
            viewModel.addNewPayment(
                binding.idCustomerPay.text.toString().toInt(),
                getPaymentDate(),
                getPaymentAmount())
        }
    }

    private fun getPaymentAmount(): Double {
        return binding.paymentAmount.text.toString().trim().toDouble()
    }

    private fun getPaymentDate(): String {
        // val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        // return sdf.format(binding.paymentDate.text.toString().trim())
        return binding.paymentDate.text.toString().trim()

    }

}