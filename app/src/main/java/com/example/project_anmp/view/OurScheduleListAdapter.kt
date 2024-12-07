package com.example.project_anmp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.project_anmp.databinding.OurScheduleListItemBinding
import com.example.project_anmp.model.ScheduleData

class OurScheduleListAdapter (val ourScheduleList:ArrayList<ScheduleData>):
RecyclerView.Adapter<OurScheduleListAdapter.OurScheduleViewHolder>(),
    OurScheduleListActionsHandler
{
    class OurScheduleViewHolder(var binding: OurScheduleListItemBinding):
    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurScheduleViewHolder {
        val binding = OurScheduleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OurScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ourScheduleList.size
    }

    override fun onBindViewHolder(holder: OurScheduleViewHolder, position: Int) {
        holder.binding.listener = this
        holder.binding.schedule = ourScheduleList[position]
    }

    // Fungsi untuk memperbarui daftar Schedule
    @SuppressLint("NotifyDataSetChanged")
    fun updateOurScheduleList(newOurScheduleList: List<ScheduleData>) {
        ourScheduleList.clear()
        ourScheduleList.addAll(newOurScheduleList)
        notifyDataSetChanged()
    }

    override fun onViewClicked(v: View, schedule: ScheduleData) {
        val action = OurScheduleFragmentDirections.actionScheduleDetail(schedule)
        Navigation.findNavController(v).navigate(action)
    }
}