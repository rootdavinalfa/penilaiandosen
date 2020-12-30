/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import xyz.dvnlabs.penilaiandosen.R
import xyz.dvnlabs.penilaiandosen.data.DataImporter
import xyz.dvnlabs.penilaiandosen.data.MainDatabase
import xyz.dvnlabs.penilaiandosen.databinding.MainFragmentBinding
import xyz.dvnlabs.penilaiandosen.ui.base.FragmentBase
import xyz.dvnlabs.penilaiandosen.utils.DialogUI
import xyz.dvnlabs.penilaiandosen.utils.Preferences

@KoinApiExtension
class FragmentMain : FragmentBase() {

    private val dataImporter: DataImporter by inject()
    private val mainDatabase: MainDatabase by inject()
    private val preferences: Preferences by inject()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = view.findNavController()
        lifecycleScope.launch {
            dataImporter.launch()
        }

        binding.mainButtonSignIn.setOnClickListener {
            lifecycleScope.launch {
                val username = binding.mainInputUsername.text.toString()
                val password = binding.mainInputPassword.text.toString()
                if (username.isEmpty() || password.isEmpty()) {
                    DialogUI.show(
                        context = requireContext(),
                        title = "Username / password kosong",
                        message = "Silahkan masukkan username / password!"
                    ) { dialog, which -> dialog.dismiss() }
                }

                if (!mainDatabase.userDAO().isUserAndPasswordOk(username, password)) {
                    DialogUI.show(
                        context = requireContext(),
                        title = "Username / password salah!",
                        message = "Silahkan coba lagi"
                    ) { dialog, which -> dialog.dismiss() }
                } else {
                    preferences.saveUsername(username)
                    navController.navigate(R.id.fragmentHome)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}