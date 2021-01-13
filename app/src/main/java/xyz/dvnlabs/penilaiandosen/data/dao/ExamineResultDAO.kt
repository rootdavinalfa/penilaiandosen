/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import xyz.dvnlabs.penilaiandosen.data.entity.ExamineResult
import xyz.dvnlabs.penilaiandosen.data.view.UserCourseTotalPenilaianDosen

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

    @RawQuery
    suspend fun findOverviewPenilaianosen(query: SupportSQLiteQuery): List<UserCourseTotalPenilaianDosen>

    //SELECT  sum(a.value) AS totalpoin,d.mkname,c.dosenname FROM examineresult a
    //INNER JOIN lecturer b ON a.lecturerid = b.lecturerid
    //INNER JOIN  dosen c ON b.dosenid = c.dosenid
    //INNER JOIN course d ON b.mkid = d.mkid
    // WHERE a.username = 'test' GROUP BY a.lecturerid
    suspend fun getOverviewPenilaianosen(username: String): List<UserCourseTotalPenilaianDosen> {
        val query =
            "SELECT  sum(a.value) AS totalPoint,d.mkname AS mkName ,c.dosenname AS dosenName FROM examineresult a \n" +
                    "INNER JOIN lecturer b ON a.lecturerid = b.lecturerid\n" +
                    "INNER JOIN  dosen c ON b.dosenid = c.dosenid\n" +
                    "INNER JOIN course d ON b.mkid = d.mkid \n" +
                    " WHERE a.username = ? GROUP BY a.lecturerid"
        val simpleSqliteQuery = SimpleSQLiteQuery(query, arrayOf(username))
        return findOverviewPenilaianosen(simpleSqliteQuery)
    }

}