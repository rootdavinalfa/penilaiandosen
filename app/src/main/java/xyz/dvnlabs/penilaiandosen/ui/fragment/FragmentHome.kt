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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import xyz.dvnlabs.penilaiandosen.data.MainDatabase
import xyz.dvnlabs.penilaiandosen.databinding.HomeFragmentBinding
import xyz.dvnlabs.penilaiandosen.ui.base.FragmentBase
import xyz.dvnlabs.penilaiandosen.ui.list.UserCourseListRV
import xyz.dvnlabs.penilaiandosen.utils.Preferences

class FragmentHome : FragmentBase() {
    private var _binding: HomeFragmentBinding? = null
    private val preferences: Preferences by inject()
    private val mainDataBase: MainDatabase by inject()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = UserCourseListRV()
        binding.homeRvCourseList.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRvCourseList.adapter = adapter
        lifecycleScope.launch {
            preferences.getUsername().collect {
                binding.homeUserName.text = it
                adapter.setNewData(mainDataBase.userCourseDAO().getUserCourseDosenByUsername(it))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}