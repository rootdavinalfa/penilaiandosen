/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.entity

import androidx.room.Entity

@Entity(tableName = "usercourse", primaryKeys = ["lecturerid", "username"])
data class UserCourse(
    var lecturerid: Int = 0,
    var username: String = ""

)
