/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.ui

import android.os.Bundle
import xyz.dvnlabs.penilaiandosen.databinding.MainActivityBinding
import xyz.dvnlabs.penilaiandosen.ui.base.ActivityBase

class MainActivity : ActivityBase() {

    lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
    }


}