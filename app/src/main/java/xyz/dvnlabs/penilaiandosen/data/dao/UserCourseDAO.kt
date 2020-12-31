/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import xyz.dvnlabs.penilaiandosen.data.entity.Courses
import xyz.dvnlabs.penilaiandosen.data.entity.Lecturer
import xyz.dvnlabs.penilaiandosen.data.entity.UserCourse
import xyz.dvnlabs.penilaiandosen.data.view.UserCourseDosenView

@Dao
interface UserCourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserCourse(userCourse: UserCourse)

    @Query("SELECT c.* FROM usercourse a INNER JOIN lecturer b ON a.lecturerid AND b.lecturerid INNER JOIN course c ON b.mkid = c.mkid WHERE a.username = :username")
    fun findCourseByUsername(username: String): Flow<List<Courses>>

    @Query("SELECT b.* FROM usercourse a INNER JOIN lecturer b ON a.lecturerid AND b.lecturerid WHERE a.username = :username")
    fun findLecturerByUsername(username: String): Flow<List<Lecturer>>

    @RawQuery
    suspend fun findUserCourseDosen(query: SupportSQLiteQuery): List<UserCourseDosenView>

    suspend fun getUserCourseDosenByUsername(username: String): List<UserCourseDosenView> {
        val query =
            "SELECT a.lecturerid,a.username,b.dosenid,b.mkid,d.mkname,c.dosenname  FROM usercourse a INNER JOIN lecturer b ON a.lecturerid = b.lecturerid INNER JOIN dosen c ON b.dosenid = c.dosenid INNER JOIN course d ON b.mkid = d.mkid WHERE a.username = ?"
        val simpleSqliteQuery = SimpleSQLiteQuery(query, arrayOf(username))
        return findUserCourseDosen(simpleSqliteQuery)
    }

}