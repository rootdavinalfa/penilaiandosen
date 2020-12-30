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
import xyz.dvnlabs.penilaiandosen.data.entity.Dosen

@Dao
interface DosenDAO {
    @Query("SELECT * FROM dosen WHERE dosenid = :dosenid")
    suspend fun findById(dosenid: Int): Dosen

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDosen(dosen: Dosen)
}