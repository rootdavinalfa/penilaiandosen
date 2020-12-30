/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.ui.base

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import xyz.dvnlabs.penilaiandosen.R

open class FragmentBase : Fragment() {
    private val appBarConfig = AppBarConfiguration(setOf(R.id.fragmentMain))
    private lateinit var toolbar: Toolbar

    override fun onStart() {
        super.onStart()
        // setup navigation with toolbar
        toolbar = requireActivity().findViewById(R.id.mainToolbar)
        val navController = requireActivity().findNavController(R.id.navigationHost)
        visibilityNavElements(navController)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfig)
    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentMain -> toolbar.visibility = View.GONE
                R.id.fragmentHome -> toolbar.visibility = View.GONE
                else -> toolbar.visibility = View.VISIBLE
            }
        }
    }

}