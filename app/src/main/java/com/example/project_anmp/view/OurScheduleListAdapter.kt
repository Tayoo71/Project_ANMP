package com.example.project_anmp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.project_anmp.databinding.OurScheduleListItemBinding
import com.example.project_anmp.model.Schedule
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OurScheduleListAdapter (val ourScheduleList:ArrayList<Schedule>):
RecyclerView.Adapter<OurScheduleListAdapter.OurScheduleViewHolder>()
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
        holder.binding.txtDateEvent.text = formatDateTime(ourScheduleList[position].datetime)
        holder.binding.txtNameEvent.text = ourScheduleList[position].event
        holder.binding.txtTeamName.text = ourScheduleList[position].game + " - " + ourScheduleList[position].team

        //When Card is Clicked
        holder.binding.cardOurSchedule.setOnClickListener {
            val action = OurScheduleFragmentDirections.actionScheduleDetail(ourScheduleList[position])
            Navigation.findNavController(it).navigate(action)
        }
    }

    // Fungsi untuk memformat datetime menjadi format 05\nSEP
    fun formatDateTime(datetime: String): String {
        // Parsing datetime dari string menjadi LocalDateTime
        val parsedDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        // Membuat formatter untuk mengambil hari dan bulan
        val dayFormatter = DateTimeFormatter.ofPattern("dd")
        val monthFormatter = DateTimeFormatter.ofPattern("MMM").withLocale(java.util.Locale.ENGLISH)

        // Mengambil hari dan bulan
        val day = parsedDateTime.format(dayFormatter)
        val month = parsedDateTime.format(monthFormatter).uppercase() // Bulan dalam huruf besar

        // Menggabungkan hari dan bulan dengan newline di antaranya
        return "$day\n$month"
    }

    // Fungsi untuk memperbarui daftar mobil
    @SuppressLint("NotifyDataSetChanged")
    fun updateOurScheduleList(newOurScheduleList: ArrayList<Schedule>) {
        ourScheduleList.clear()
        ourScheduleList.addAll(newOurScheduleList)
        notifyDataSetChanged()
    }
}