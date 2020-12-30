/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.ui.base

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class ActivityBase : AppCompatActivity() {
    protected open fun changeStatusBar(
        activity: Activity,
        color: Int,
        lightThemeIcon: Boolean = true
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            if (lightThemeIcon) {
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            window.decorView.systemUiVisibility = flags
            activity.window.statusBarColor = getColor(color)
        }
    }
}