package com.example.jobdeskapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jobdeskapp.model.Jobdesk
import kotlinx.coroutines.flow.Flow

@Dao
interface JobdeskDao {
    @Query("SELECT * FROM  `jobdesk_table` ORDER BY name ASC")
    fun getAllJobdesk(): Flow<List<Jobdesk>>

    @Insert
    suspend fun insertJobdesk(jobdesk: Jobdesk)

    @Delete
    suspend fun deleteJobdesk(jobdesk: Jobdesk)

    @Update
    fun updateJobdesk(jobdesk: Jobdesk)

}