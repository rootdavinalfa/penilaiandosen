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
import xyz.dvnlabs.penilaiandosen.data.entity.Dosen
import xyz.dvnlabs.penilaiandosen.data.entity.Lecturer

@Dao
interface LecturerDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLecturer(lecturer: Lecturer)

    @Query("SELECT * FROM lecturer WHERE mkid = :mkid AND lecturerid =:lecturerId AND dosenid = :dosenId")
    suspend fun findById(mkid: Int, lecturerId: Int, dosenId: Int) : Lecturer

    @Query("SELECT b.* FROM lecturer a INNER JOIN course b ON a.mkid = b.mkid WHERE a.lecturerid = :lecturerId")
    fun findCourseByLecturerid(lecturerId: Int): Flow<List<Courses>>

    @Query("SELECT b.* FROM lecturer a INNER JOIN dosen b ON a.dosenid = b.dosenid WHERE a.lecturerid = :lecturerId")
    fun findDosenByLecturerid(lecturerId: Int): Flow<List<Dosen>>

}