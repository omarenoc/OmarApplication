package com.example.omarapplication.data.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
data class Customer (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_customer")
    val customerId: Int = 0,

    @NotNull @ColumnInfo(name = "name")
    val customerName: String,

    @ColumnInfo(name = "middle_name")
    val middleName: String,

    @NotNull @ColumnInfo(name = "last_name")
    val lastName: String,

    @NotNull @ColumnInfo(name = "second_last_name")
    val secondLastName: String,

    @NotNull @ColumnInfo(name = "birthdate")
    val birthdate: String,

    @NotNull @ColumnInfo(name = "gender")
    val gender: Int
    )