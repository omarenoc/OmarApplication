package com.example.omarapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.omarapplication.OmarApplication
import com.example.omarapplication.data.entities.Customer
import com.example.omarapplication.databinding.FragmentAddCustomerBinding
import com.example.omarapplication.viewmodel.AppViewModel
import com.example.omarapplication.viewmodel.AppViewModelFactory

class AddCustomerFragment : Fragment() {
    private val navigationArgs: AddCustomerFragmentArgs by navArgs()

    private var _binding: FragmentAddCustomerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppViewModel by activityViewModels {
        AppViewModelFactory(
            (activity?.application as OmarApplication).database.customerDao(),
            (activity?.application as OmarApplication).database.paymentDao()
        )
    }

    lateinit var customer: Customer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.clientId
        if (id > 0) {
            viewModel.retrieveCustomer(id).observe(this.viewLifecycleOwner) { selectedCustomer ->
                customer = selectedCustomer
                bind(customer)
            }
        } else {
            binding.saveCustomer.setOnClickListener { addNewCustomer() }
        }

    }


    private fun isCustomerValid(): Boolean {
        return viewModel.isCustomerValid(
            binding.customerName.text.toString(),
            binding.customerLastname.text.toString(),
            binding.customerSecondLastname.text.toString(),
            getCustomerBirthdate()
        )
    }

    private fun getCustomerBirthdate(): String {
        //val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        //return sdf.format(binding.customerBirthdate.text.toString().trim())
        return binding.customerBirthdate.text.toString().trim()
    }

    private fun addNewCustomer() {
        if (isCustomerValid()) {
            viewModel.addNewCustomer(
                binding.customerName.text.toString().trim(),
                binding.customerMiddlename.text.toString().trim(),
                binding.customerLastname.text.toString().trim(),
                binding.customerSecondLastname.text.toString().trim(),
                getCustomerBirthdate(),
                binding.optionMale.isChecked()
            )
        }
        val action = AddCustomerFragmentDirections.actionAddCustomerFragmentToCustomerFragment()
        findNavController().navigate(action)
    }

    private fun bind(customer: Customer) {
        binding.apply {
            customerName.setText(customer.customerName, TextView.BufferType.SPANNABLE)
            customerMiddlename.setText(customer.middleName, TextView.BufferType.SPANNABLE)
            customerLastname.setText(customer.lastName, TextView.BufferType.SPANNABLE)
            customerSecondLastname.setText(customer.secondLastName, TextView.BufferType.SPANNABLE)
            customerBirthdate.setText(customer.birthdate, TextView.BufferType.SPANNABLE)
            saveCustomer.setOnClickListener { updateCustomer() }
        }
        showCustomerGender(customer.gender)
    }

    private fun showCustomerGender(gender: Int) {
        if (gender == 1) {
            binding.optionMale.isChecked = true
            binding.optionFemale.isChecked = false
        } else if (gender == 2) {
            binding.optionFemale.isChecked = true
            binding.optionMale.isChecked = false
        }
    }


    private fun updateCustomer() {
        if (isCustomerValid()) {
            viewModel.updateCustomer(
                this.navigationArgs.clientId,
                this.binding.customerName.text.toString().trim(),
                this.binding.customerMiddlename.text.toString().trim(),
                this.binding.customerLastname.text.toString().trim(),
                this.binding.customerSecondLastname.text.toString().trim(),
                this.binding.customerBirthdate.text.toString().trim(),
                this.binding.optionMale.isChecked()
            )
            val action = AddCustomerFragmentDirections.actionAddCustomerFragmentToCustomerFragment()
            findNavController().navigate(action)
        }
    }

}