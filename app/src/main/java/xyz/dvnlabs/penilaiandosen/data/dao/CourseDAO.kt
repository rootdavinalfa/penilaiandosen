/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.dvnlabs.penilaiandosen.data.entity.Courses

@Dao
interface CourseDAO {
    @Query("SELECT * FROM course WHERE mkid = :mkid")
    suspend fun findById(mkid: String): Courses

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(courses: Courses)
}