package com.example.omarapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.omarapplication.data.entities.Payment
import com.example.omarapplication.databinding.FragmentPaymentItemBinding


class PaymentRecyclerViewAdapter() :
    ListAdapter<Payment, PaymentRecyclerViewAdapter.PaymentViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(
            FragmentPaymentItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val current = getItem(position)
        holder.dateView.text = current.date
        holder.amountView.text = current.amount.toString()
        holder.idView.text = current.customerIdPay.toString()
    }


    inner class PaymentViewHolder(binding: FragmentPaymentItemBinding): RecyclerView.ViewHolder(binding.root) {
        val dateView: TextView = binding.itemDate
        val amountView: TextView = binding.itemAmount
        val idView: TextView = binding.itemId
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Payment>() {
            override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
                return oldItem.paymentId == newItem.paymentId
            }

        }
    }
}

