/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xyz.dvnlabs.penilaiandosen.data.dao.*
import xyz.dvnlabs.penilaiandosen.data.entity.*

@Database(
    entities = [User::class, UserCourse::class, Lecturer::class,
        Dosen::class, Courses::class, ExamineResult::class,Question::class
    ], version = 1
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun courseDAO(): CourseDAO
    abstract fun dosenDAO(): DosenDAO
    abstract fun examineResultDAO(): ExamineResultDAO
    abstract fun lecturerDAO(): LecturerDAO
    abstract fun userCourseDAO(): UserCourseDAO
    abstract fun questionDAO(): QuestionDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_database.db"
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}