/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class DialogUI {

    companion object {
        fun show(
            context: Context,
            title: String = "",
            message: String = "",
            positiveText: String = "OK",
            positive: DialogInterface.OnClickListener
        ) {
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                    positiveText, positive
                )
                .show()
        }
    }

}