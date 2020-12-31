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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import xyz.dvnlabs.penilaiandosen.data.MainDatabase
import xyz.dvnlabs.penilaiandosen.data.entity.ExamineResult
import xyz.dvnlabs.penilaiandosen.databinding.AnswerFragmentBinding

class AnswerSheet : BottomSheetDialogFragment() {

    private var _binding: AnswerFragmentBinding? = null
    private val mainDataBase: MainDatabase by inject()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AnswerFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val username = requireArguments().getString("username", "")
        val lecturerid = requireArguments().getInt("lecturerid", 0)
        val qid = requireArguments().getInt("qid", 0)
        println("FRAGMENT $qid")
        binding.sangatBaikBtn.setOnClickListener {
            lifecycleScope.launch {
                insertSheetAnswer(4, lecturerid, username, qid)
            }
        }

        binding.baikBtn.setOnClickListener {
            lifecycleScope.launch { insertSheetAnswer(3, lecturerid, username, qid) }

        }

        binding.cukupBtn.setOnClickListener {
            lifecycleScope.launch {
                insertSheetAnswer(2, lecturerid, username, qid)
            }
        }

        binding.kurangBtn.setOnClickListener {
            lifecycleScope.launch {
                insertSheetAnswer(1, lecturerid, username, qid)
            }
        }


        super.onViewCreated(view, savedInstanceState)
    }

    private suspend fun insertSheetAnswer(value: Int, lecturerid: Int, username: String, qid: Int) {
        val examineResult =
            mainDataBase.examineResultDAO().findByIdNonFlow(username, lecturerid, qid)
        println("value:: $value qid:: $qid Lecturer:: $lecturerid ER:: $examineResult")
        if (examineResult == null) {
            mainDataBase.examineResultDAO()
                .insertExamineResult(ExamineResult(lecturerid, username, value, qid))
        } else {
            mainDataBase.examineResultDAO()
                .updateExamineResult(ExamineResult(lecturerid, username, value, qid))
        }
        this.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}