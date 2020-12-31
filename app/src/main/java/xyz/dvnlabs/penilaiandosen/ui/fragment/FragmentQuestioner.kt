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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import xyz.dvnlabs.penilaiandosen.data.MainDatabase
import xyz.dvnlabs.penilaiandosen.databinding.QuetionerFragmentBinding
import xyz.dvnlabs.penilaiandosen.ui.base.FragmentBase
import xyz.dvnlabs.penilaiandosen.ui.list.QuestionListRv
import xyz.dvnlabs.penilaiandosen.utils.Preferences

class FragmentQuestioner : FragmentBase() {
    private var _binding: QuetionerFragmentBinding? = null
    private val preferences: Preferences by inject()
    private val mainDataBase: MainDatabase by inject()
    val args: FragmentQuestionerArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QuetionerFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @KoinApiExtension
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.questionBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val adapter = QuestionListRv(requireContext(), args.lecturerid, args.username,args.dosenid)
        binding.questionRvList.layoutManager = LinearLayoutManager(requireContext())
        binding.questionRvList.adapter = adapter

        lifecycleScope.launch {
            val dosen = mainDataBase.dosenDAO().findById(args.dosenid)

            binding.questionDosenName.text = dosen.dosenname
            binding.questionMkName.text = args.mkname

            val groups = mainDataBase.questionDAO().getQuestionGroup()
            groups.collect {
                adapter.setData(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}