/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.entity

import androidx.room.Entity

@Entity(tableName = "lecturer", primaryKeys = ["dosenid", "lecturerid", "mkid"])
data class Lecturer(
    var dosenid: Int = 0,
    var lecturerid: Int = 0,
    var mkid: Int = 0
)
