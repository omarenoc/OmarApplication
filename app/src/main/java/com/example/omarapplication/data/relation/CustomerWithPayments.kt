package com.example.omarapplication.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.omarapplication.data.entities.Customer
import com.example.omarapplication.data.entities.Payment

class CustomerWithPayments (
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "id_customer",
        entityColumn = "id_customer_p"
    )
    val payments: List<Payment>
)