/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
data class Courses(
    @PrimaryKey var mkid: Int = 0,
    var mkname: String = ""
)
