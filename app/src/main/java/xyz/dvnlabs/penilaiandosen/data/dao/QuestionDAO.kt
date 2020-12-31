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
import xyz.dvnlabs.penilaiandosen.data.entity.Question

@Dao
interface QuestionDAO {

    @Query("SELECT * FROM question")
    fun findAllQuestion(): Flow<List<Question>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestion(question: Question)

    @Query("SELECT `group` FROM question GROUP BY `group`")
    fun getQuestionGroup(): Flow<List<String>>

    @Query("SELECT * FROM question WHERE `group` = :grp ")
    fun getQuestionByGroup(grp: String): Flow<List<Question>>

}