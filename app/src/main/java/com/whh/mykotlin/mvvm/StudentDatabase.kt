package com.whh.mykotlin.mvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun getStudentDao(): StudentDao

    companion object {
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    StudentDatabase::class.java,
                    "student_database.db"
                ).allowMainThreadQueries() //允许在主线程中访问数据库
                    .build()
            }
            return INSTANCE;
        }

        fun getDatabase(): StudentDatabase? = INSTANCE
    }


}