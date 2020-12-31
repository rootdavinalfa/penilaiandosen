/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import xyz.dvnlabs.penilaiandosen.data.view.UserCourseDosenView
import xyz.dvnlabs.penilaiandosen.databinding.RvDosenBinding
import xyz.dvnlabs.penilaiandosen.ui.fragment.FragmentHomeDirections
import xyz.dvnlabs.penilaiandosen.utils.ItemDiff

class UserCourseListRV :
    RecyclerView.Adapter<UserCourseListRV.ViewHolder>() {

    private var userCourses: List<UserCourseDosenView> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvDosenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userCourses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem()
    }

    fun setNewData(data: List<UserCourseDosenView>) {
        val diffCallback =
            ItemDiff(this.userCourses, data, arrayOf("lecturerid", "dosenid", "mkid"))
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.userCourses = data
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(val binding: RvDosenBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var course: UserCourseDosenView? = null

        init {
            binding.root.setOnClickListener(this)
        }

        fun bindItem() {
            course = userCourses[adapterPosition]
            binding.rvDosenName.text = course?.dosenname
            binding.rvMkText.text = course?.mkname
        }

        override fun onClick(v: View?) {
            course.let {
                if (it != null) {
                    val action = FragmentHomeDirections.actionFragmentHomeToFragmentQuestioner(
                        it.username,
                        it.dosenid,
                        it.mkname,
                        it.lecturerid
                    )
                    v?.findNavController()?.navigate(action)

                }
            }
        }

    }

}