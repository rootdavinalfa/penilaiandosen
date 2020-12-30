/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class Question(
    @PrimaryKey var qid : Int = 0,
    var question : String = "",
    var group : String = ""
)