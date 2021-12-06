package com.example.omarapplication

import android.app.Application
import com.example.omarapplication.data.MatrixDatabase

class OmarApplication: Application() {
    val database: MatrixDatabase by lazy { MatrixDatabase.getDatabase(this) }
}