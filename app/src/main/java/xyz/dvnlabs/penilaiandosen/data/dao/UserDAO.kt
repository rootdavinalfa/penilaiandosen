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
import xyz.dvnlabs.penilaiandosen.data.entity.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user where user = :user")
    suspend fun findByUser(user: String): User

    @Query("SELECT EXISTS(SELECT * FROM User WHERE user = :username AND password = :password)")
    suspend fun isUserAndPasswordOk(username: String, password: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun newUser(user: User)

    @Query("SELECT * FROM user")
    suspend fun getAllUser(): List<User>


}