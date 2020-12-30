/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.entity

import androidx.room.Entity

@Entity(tableName = "examineresult",primaryKeys = ["lecturerid","username","qid"])
data class ExamineResult(
    var lecturerid: Int = 0,
    var username: String ="",
    var value: Int = 0,
    var qid : Int = 0
)
