package com.example.jobdeskapp.application

import android.app.Application
import com.example.jobdeskapp.repository.JobdeskRepository

class JobdeskApp: Application() {
    val database by lazy {JobdeskDatabase.getDatabase(this) }
    val repository by lazy { JobdeskRepository(database.jobdeskDao()) }
}