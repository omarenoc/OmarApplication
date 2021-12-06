package com.example.omarapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.omarapplication.data.dao.CustomerDao
import com.example.omarapplication.data.dao.PaymentDao
import com.example.omarapplication.data.entities.Customer
import com.example.omarapplication.data.entities.Payment

@Database(entities = [Customer::class, Payment::class], version = 1, exportSchema = false)
abstract class MatrixDatabase: RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun paymentDao(): PaymentDao

    companion object {
        @Volatile
        private var instance: MatrixDatabase? = null

        fun getDatabase(context: Context): MatrixDatabase {

            return instance ?: synchronized(this) {
                val roomInstance = Room.databaseBuilder(
                    context.applicationContext,
                    MatrixDatabase::class.java,
                    "matrix_database"
                ).fallbackToDestructiveMigration().build()
                instance = roomInstance
                return roomInstance
            }

        }
    }

}