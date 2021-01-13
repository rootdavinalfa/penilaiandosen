/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.utils

import androidx.recyclerview.widget.DiffUtil
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

class ItemDiff(val old: List<Any>, val new: List<Any>, val fieldCompares: Array<String>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean {
        var temp = true
        fieldCompares.forEach { fieldCompare ->
            val temp1: Boolean
            val oldValue = old[oldItemPosition]::class.memberProperties
                .filter { it.visibility == KVisibility.PUBLIC }
                .filterIsInstance<KMutableProperty<*>>()
                .first { it.getter.name == fieldCompare }.getter.call(old[oldItemPosition])

            val newValue = new[newItemPosition]::class.memberProperties
                .filter { it.visibility == KVisibility.PUBLIC }
                .filterIsInstance<KMutableProperty<*>>()
                .first { it.getter.name == fieldCompare }.getter.call(new[newItemPosition])
            println("OLDVAL :$oldValue NEWVAL: $newValue")
            temp1 = oldValue == newValue
            temp = temp == temp1
        }

        return temp
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}