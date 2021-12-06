package com.example.omarapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omarapplication.adapters.PaymentRecyclerViewAdapter
import com.example.omarapplication.OmarApplication
import com.example.omarapplication.databinding.FragmentPaymentItemListBinding
import com.example.omarapplication.viewmodel.AppViewModel
import com.example.omarapplication.viewmodel.AppViewModelFactory


class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppViewModel by activityViewModels {
        AppViewModelFactory(
            (activity?.application as OmarApplication).database.customerDao(),
            (activity?.application as OmarApplication).database.paymentDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PaymentRecyclerViewAdapter()
        binding.list.adapter = adapter
        viewModel.paymentsList.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        binding.list.layoutManager = LinearLayoutManager(this.context)
    }
}