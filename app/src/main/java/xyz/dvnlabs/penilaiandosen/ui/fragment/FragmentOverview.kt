/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension
import xyz.dvnlabs.penilaiandosen.data.view.UserCourseTotalPenilaianDosen
import xyz.dvnlabs.penilaiandosen.databinding.OverviewFragmentBinding
import xyz.dvnlabs.penilaiandosen.ui.base.FragmentBase
import xyz.dvnlabs.penilaiandosen.ui.vm.DataViewModel
import xyz.dvnlabs.penilaiandosen.utils.Preferences

class FragmentOverview : FragmentBase() {

    private var _binding: OverviewFragmentBinding? = null
    private val preferences: Preferences by inject()
    private val dataVM: DataViewModel by sharedViewModel()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OverviewFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @KoinApiExtension
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            preferences.getUsername().collect {
                dataVM.getTotalPenilaianDosen(it).observe(viewLifecycleOwner, { list ->
                    createBar(list)
                })
            }
        }
    }

    private fun createBar(data: List<UserCourseTotalPenilaianDosen>) {
        /*val dosenSet = ArrayList<BarEntry>()
        val dosenSeto = ArrayList<Any>()
        val mkSet :Array<BarDataSet>
        val mkSeto = ArrayList<Any>()
        val xSet = ArrayList<String>()
        var tempDosen = ""
        var temp = 0f
        data.forEach {
            if (temp == 0f){
                tempDosen = it.dosenName
            }
            dosenSet.add(BarEntry(it.totalPoint.toFloat(), temp))

            if (tempDosen != it.dosenName) {
                mkSet.add(BarDataSet(dosenSet, it.dosenName))
                temp = 0f
            } else {
                tempDosen = it.dosenName
                temp++
            }
        }
        data.map { it.mkName }.distinct().forEach {
            xSet.add(it)
        }
        //val barDataSet = BarDataSet(dosenSet,"Dosen")
        binding.overviewTingkatPenilaianDosen.data = BarData())
*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}