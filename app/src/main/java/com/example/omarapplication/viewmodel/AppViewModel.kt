package com.example.omarapplication.viewmodel

import androidx.lifecycle.*
import com.example.omarapplication.data.entities.Customer
import com.example.omarapplication.data.dao.CustomerDao
import com.example.omarapplication.data.entities.Payment
import com.example.omarapplication.data.dao.PaymentDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class AppViewModel(private val customerDao: CustomerDao, private val paymentDao: PaymentDao): ViewModel() {

    val paymentsList : LiveData<List<Payment>> = paymentDao.getPaymentsListbyDate().asLiveData()
    val customersList : LiveData<List<Customer>> = customerDao.getCustomersList().asLiveData()

    fun retrieveCustomer(id: Int): LiveData<Customer> {
        return customerDao.getCustomer(id).asLiveData()
    }

    private fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            customerDao.addCustomer(customer)
        }
    }

    private fun addPayment(payment: Payment) {
        viewModelScope.launch {
            paymentDao.addPayment(payment)
        }
    }

    private fun updateCustomer(customer:Customer) {
        viewModelScope.launch {
            customerDao.editCustomer(customer)
        }
    }

    private fun getnewCustomerEntry(
        customerName: String,
        customerMiddleName: String,
        customerLastName: String,
        customerSecondLastName:String,
        customerBirthdate:String,
        customerGender:Boolean
    ): Customer {
        return Customer(
            customerName = customerName,
            middleName = customerMiddleName,
            lastName = customerLastName,
            secondLastName = customerSecondLastName,
            birthdate = customerBirthdate,
            gender = getCustomerGender(customerGender)
        )
    }

    private fun getUpdatedCustomerEntry(
        customerId: Int,
        customerName: String,
        customerMiddleName: String,
        customerLastName: String,
        customerSecondLastName:String,
        customerBirthdate:String,
        customerGender:Boolean
    ): Customer {
        return Customer(
            customerId = customerId,
            customerName = customerName,
            middleName = customerMiddleName,
            lastName = customerLastName,
            secondLastName = customerSecondLastName,
            birthdate = customerBirthdate,
            gender = getCustomerGender(customerGender)
        )
    }

    fun getNewPaymentEntry(
        customerId: Int,
        date:String,
        amount:Double
    ): Payment {
        return Payment(
            customerIdPay = customerId,
            registerDate = Calendar.DATE.toString(),
            date = date,
            amount = amount
        )
    }

    fun addNewCustomer(customerName: String, customerMiddleName: String, customerLastName: String, customerSecondLastName: String, customerBirthdate: String, customerGender: Boolean) {
        val newCustomer = getnewCustomerEntry(
            customerName, customerMiddleName,
            customerLastName, customerSecondLastName,
            customerBirthdate, customerGender
        )
        addCustomer(newCustomer)
    }

    fun addNewPayment(customerId: Int, date: String, amount: Double) {
        val newPayment = getNewPaymentEntry(customerId, date, amount)
        addPayment(newPayment)
    }

    fun isCustomerValid(
        customerName: String,
        customerLastName:String,
        customerSecondLastName:String,
        customerBirthdate:String,
    ): Boolean {
        if (
            customerName.isBlank() ||
            customerLastName.isBlank() ||
            customerSecondLastName.isBlank() ||
            customerBirthdate.isBlank()
        ) {
            return false
        }
        return true
    }

    fun isPaymentValid(
        customerId: Int,
        date: String,
        amount: Double): Boolean {
        if (
            customerId == 0 ||
            date.isEmpty() ||
            amount == 0.toDouble()
        ) {
            return false
        }
        return true
    }

    fun getCustomerGender(isCustomerMale:Boolean): Int {
        if (isCustomerMale) {
            return 1
        } else return 2
    }

    fun getGenderLabel(gender: Int): String {
        return when(gender) {
            1 -> "Masculino"
            2 -> "Femenino"
            else -> "Error"
        }
    }

    fun updateCustomer(
        customerId: Int,
        customerName: String,
        customerMiddleName: String,
        customerLastName: String,
        customerSecondLastName:String,
        customerBirthdate:String,
        customerGender:Boolean
    ) {
        val updatedCustomer = getUpdatedCustomerEntry(
            customerId,
            customerName,
            customerMiddleName,
            customerLastName,
            customerSecondLastName,
            customerBirthdate,
            customerGender)
        updateCustomer(updatedCustomer)

    }


}

class AppViewModelFactory(private val customerDao: CustomerDao, private val paymentDao: PaymentDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(customerDao, paymentDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}