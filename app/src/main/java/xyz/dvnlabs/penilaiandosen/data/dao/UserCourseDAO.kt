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
import kotlinx.coroutines.flow.Flow
import xyz.dvnlabs.penilaiandosen.data.entity.Courses
import xyz.dvnlabs.penilaiandosen.data.entity.Lecturer
import xyz.dvnlabs.penilaiandosen.data.entity.UserCourse

@Dao
interface UserCourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserCourse(userCourse: UserCourse)

    @Query("SELECT c.* FROM usercourse a INNER JOIN lecturer b ON a.lecturerid AND b.lecturerid INNER JOIN course c ON b.mkid = c.mkid WHERE a.username = :username")
    fun findCourseByUsername(username: String): Flow<List<Courses>>

    @Query("SELECT b.* FROM usercourse a INNER JOIN lecturer b ON a.lecturerid AND b.lecturerid WHERE a.username = :username")
    fun findLecturerByUsername(username: String): Flow<List<Lecturer>>

}