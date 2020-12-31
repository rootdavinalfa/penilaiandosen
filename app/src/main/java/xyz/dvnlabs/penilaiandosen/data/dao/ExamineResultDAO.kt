/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import xyz.dvnlabs.penilaiandosen.data.entity.ExamineResult

@Dao
interface ExamineResultDAO {

    @Query("SELECT * FROM examineresult WHERE lecturerid = :lecturerId AND username = :user AND qid = :qid")
    fun findById(user: String, lecturerId: Int, qid: Int): Flow<ExamineResult?>

    @Query("SELECT * FROM examineresult WHERE lecturerid = :lecturerId AND username = :user AND qid = :qid")
    suspend fun findByIdNonFlow(user: String, lecturerId: Int, qid: Int): ExamineResult?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertExamineResult(examineResult: ExamineResult)

    @Update
    suspend fun updateExamineResult(examineResult: ExamineResult)

}