package com.example.jobdeskapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "jobdesk_table")
data class Jobdesk  (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val phonenumber: String,
    val latitude: Double?,
    val longitude: Double?
) : Parcelable