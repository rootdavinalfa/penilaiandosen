/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import xyz.dvnlabs.penilaiandosen.data.MainDatabase
import xyz.dvnlabs.penilaiandosen.databinding.RvQuestionGroupBinding

@KoinApiExtension
class QuestionListRv(
    private val context: Context,
    private val lecturerid: Int,
    private val username: String,
    private val dosenid: Int
) :
    RecyclerView.Adapter<QuestionListRv.ViewHolder>(), KoinComponent {

    private var datas: List<String> = emptyList()
    private val mainDatabase: MainDatabase by inject {
        parametersOf(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvQuestionGroupBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem()
    }

    fun setData(data: List<String>) {
        val diffCallback =
            ItemDiff(this.datas, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.datas = data
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val binding: RvQuestionGroupBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        var dat: String? = null
        fun bindItem() {
            dat = datas[adapterPosition]
            if (dat != null) {
                binding.rvGroupName.text = when (dat) {
                    "A" -> {
                        "$dat - Persiapan"
                    }
                    "B" -> {
                        "$dat - Pelaksanaan"
                    }
                    "C" -> {
                        "$dat - Penilaian Hasil Belajar Mahasiswa"
                    }
                    else -> {
                        "$dat - Group Pertanyaan"
                    }
                }
                val adapter = QuestionDetailRV(context, lecturerid, dosenid, username)
                binding.rvQuestionList.layoutManager = LinearLayoutManager(context)
                binding.rvQuestionList.adapter = adapter
                CoroutineScope(Dispatchers.Main).launch {
                    val questioner = mainDatabase.questionDAO().getQuestionByGroup(dat!!)
                    questioner.collect {
                        adapter.setData(it)
                    }
                }
            }
        }

        override fun onClick(v: View?) {
            binding.rvGroupExpand.toggle()
        }
    }

    class ItemDiff(val old: List<String>, val new: List<String>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return old.size
        }

        override fun getNewListSize(): Int {
            return new.size
        }

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }
    }


}