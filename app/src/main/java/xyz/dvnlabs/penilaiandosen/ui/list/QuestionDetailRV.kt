/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.ui.list

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import xyz.dvnlabs.penilaiandosen.R
import xyz.dvnlabs.penilaiandosen.data.MainDatabase
import xyz.dvnlabs.penilaiandosen.data.entity.Question
import xyz.dvnlabs.penilaiandosen.databinding.RvQuestionBinding
import xyz.dvnlabs.penilaiandosen.ui.fragment.AnswerSheet
import xyz.dvnlabs.penilaiandosen.utils.ItemDiff

@KoinApiExtension
class QuestionDetailRV(
    val context: Context,
    val lecturerid: Int,
    val dosenid: Int,
    val username: String
) :
    RecyclerView.Adapter<QuestionDetailRV.ViewHolder>(), KoinComponent {
    private val mainDatabase: MainDatabase by inject {
        parametersOf(context)
    }
    private var datas: List<Question> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvQuestionBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem()
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    fun setData(question: List<Question>) {
        val diffCallback =
            ItemDiff(this.datas, question, arrayOf("qid"))
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.datas = question
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val binding: RvQuestionBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        var question: Question? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bindItem() {
            question = datas[adapterPosition]
            binding.rvQuestionText.text = question?.question
            binding.rvQuestionStatus.text = "Belum dinilai"

            if (question != null) {
                CoroutineScope(Dispatchers.Main).launch {
                    mainDatabase.examineResultDAO()
                        .findById(username, lecturerid, question!!.qid)
                        .collect { examineResult ->
                            examineResult?.let {
                                println("EXAMINERESULT:: $it")
                                binding.rvQuestionStatus.text = when (it.value) {
                                    1 -> {
                                        colorStatus(
                                            R.color.black,
                                            R.color.red
                                        )
                                        "Kurang"
                                    }
                                    2 -> {
                                        colorStatus(
                                            R.color.black,
                                            R.color.yellow
                                        )
                                        "Cukup"
                                    }
                                    3 -> {
                                        colorStatus(
                                            R.color.black,
                                            R.color.green
                                        )
                                        "Baik"
                                    }
                                    4 -> {
                                        colorStatus(
                                            R.color.black,
                                            R.color.purple_200
                                        )
                                        "Sangat Baik"
                                    }
                                    else -> {
                                        "Tidak Ada Nilai"
                                    }
                                }

                            }

                        }
                }
            }

        }

        override fun onClick(v: View?) {
            val bundle = Bundle()

            bundle.putInt("lecturerid", lecturerid)
            bundle.putString("username", username)
            question?.let {
                println("QID ${it.qid}")
                bundle.putInt("qid", it.qid)
            }
            val fragment = AnswerSheet()
            context as AppCompatActivity
            fragment.arguments = bundle
            fragment.show(context.supportFragmentManager, "AnswerSheet")
        }

        private fun colorStatus(colorRes: Int = 0, colorResTint: Int = 0) {
            if (colorRes != 0) {
                binding.rvQuestionStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        colorRes
                    )
                )
            }

            if (colorResTint != 0) {
                ViewCompat.setBackgroundTintList(
                    binding.rvQuestionStatus,
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            colorResTint
                        )
                    )
                )
            }
        }
    }
}