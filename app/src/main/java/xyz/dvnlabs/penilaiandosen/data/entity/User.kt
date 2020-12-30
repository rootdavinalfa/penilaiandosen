/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey var user: String = "",
    var password: String = ""
)
