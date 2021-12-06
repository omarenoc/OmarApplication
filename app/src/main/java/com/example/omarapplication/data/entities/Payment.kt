package com.example.omarapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
data class Payment (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_payment")
    var paymentId: Int = 0,

    @NotNull @ColumnInfo(name = "id_customer_p")
    var customerIdPay: Int,

    @NotNull @ColumnInfo(name = "register_date")
    var registerDate: String,

    @NotNull @ColumnInfo(name = "payment_date")
    var date: String,

    @NotNull @ColumnInfo(name = "amount")
    var amount: Double
    )