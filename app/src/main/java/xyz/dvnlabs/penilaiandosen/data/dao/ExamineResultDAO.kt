/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.dao

import androidx.room.Dao
import androidx.room.Query
import xyz.dvnlabs.penilaiandosen.data.entity.ExamineResult

@Dao
interface ExamineResultDAO {

    @Query("SELECT * FROM examineresult WHERE lecturerid = :lecturerId AND username = :user")
    suspend fun findById(user : String,lecturerId : Int) : ExamineResult


}