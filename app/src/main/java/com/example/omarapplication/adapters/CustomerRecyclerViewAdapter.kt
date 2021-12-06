package com.example.omarapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.omarapplication.data.entities.Customer
import com.example.omarapplication.databinding.FragmentCustomerItemBinding

class CustomerRecyclerViewAdapter(private val onCustomerClicked: (Customer) -> Unit) :
    ListAdapter<Customer, CustomerRecyclerViewAdapter.CustomerViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            FragmentCustomerItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onCustomerClicked(current)
        }
        holder.bind(current)
    }

    class CustomerViewHolder(private var binding: FragmentCustomerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(customer: Customer) {
                binding.apply {
                    itemNamePh.text = customer.customerName
                    itemLastNamePh.text = customer.lastName
                    itemSecondLastNamePh.text = customer.secondLastName
                }
            }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.customerId == newItem.customerId
            }
        }
    }


}