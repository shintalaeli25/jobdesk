package com.example.jobdeskapp.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jobdeskapp.dao.JobdeskDao
import com.example.jobdeskapp.model.Jobdesk

@Database(entities = [Jobdesk::class], version = 2, exportSchema = false)
abstract class JobdeskDatabase: RoomDatabase(){
    abstract fun jobdeskDao(): JobdeskDao

    companion object{
        private var INSTANCE: JobdeskDatabase? = null

        private val migration1To2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE jobdesk_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE jobdesk_table ADD COLUMN longitude Double DEFAULT 0.0")
            }
        }

        fun getDatabase(context: Context): JobdeskDatabase {
            return  INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JobdeskDatabase::class.java,
                    "Jobdesk_database"
                )
                    .addMigrations(migration1To2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE= instance
                instance
            }
        }
    }
}