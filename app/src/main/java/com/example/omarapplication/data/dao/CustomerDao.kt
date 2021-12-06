package com.example.omarapplication.data.dao

import androidx.room.*
import com.example.omarapplication.data.entities.Customer
import com.example.omarapplication.data.relation.CustomerWithPayments
import kotlinx.coroutines.flow.Flow


@Dao
interface CustomerDao  {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCustomer(newCustomer: Customer)

    @Update
    suspend fun editCustomer(customer: Customer)

    @Query("SELECT * FROM customer WHERE id_customer = :id")
    fun getCustomer(id: Int): Flow<Customer>

    @Query("SELECT * FROM customer ORDER BY last_name ASC")
    fun getCustomersList(): Flow<List<Customer>>

    @Transaction
    @Query("SELECT * FROM customer WHERE id_customer=:customerId")
    fun getCustomerWithPayments(customerId: Int): Flow<List<CustomerWithPayments>>
}