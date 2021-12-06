package com.example.omarapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.omarapplication.data.entities.Payment
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addPayment(payment: Payment)

    @Query("SELECT * FROM payment GROUP BY payment_date ORDER BY amount DESC")
    fun getPaymentsListbyDate(): Flow<List<Payment>>

}