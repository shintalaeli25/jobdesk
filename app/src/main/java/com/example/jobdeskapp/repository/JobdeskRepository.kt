package com.example.jobdeskapp.repository

import com.example.jobdeskapp.dao.JobdeskDao
import com.example.jobdeskapp.model.Jobdesk
import kotlinx.coroutines.flow.Flow

class JobdeskRepository(private val jobdeskDao: JobdeskDao) {
    val allJobdesk: Flow<List<Jobdesk>> = jobdeskDao.getAllJobdesk()

    suspend fun insertJobdesk(jobdesk: Jobdesk){
        jobdeskDao.insertJobdesk(jobdesk)
    }
    suspend fun deleteJobdesk(jobdesk: Jobdesk){
        jobdeskDao.deleteJobdesk(jobdesk)
    }
    suspend fun updateJobdesk(jobdesk: Jobdesk){
        jobdeskDao.updateJobdesk(jobdesk)
    }
}