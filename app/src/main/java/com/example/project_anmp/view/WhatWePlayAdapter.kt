package com.example.project_anmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.project_anmp.databinding.WwpListItemBinding
import com.example.project_anmp.model.Game
import com.squareup.picasso.Picasso

class WhatWePlayAdapter(val whatWePlayList: ArrayList<Game>) : RecyclerView.Adapter<WhatWePlayAdapter.WhatWePlayViewHolder>() {

    class WhatWePlayViewHolder(val binding: WwpListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhatWePlayViewHolder {
        val binding = WwpListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WhatWePlayViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return whatWePlayList.size
    }

    override fun onBindViewHolder(holder: WhatWePlayViewHolder, position: Int) {
        holder.binding.txtNamaGame.text = whatWePlayList[position].name
        holder.binding.txtDesc.text = whatWePlayList[position].description
        Picasso.get()
            .load(whatWePlayList[position].urlLink)
            .into(holder.binding.imageView)
        holder.binding.btnTeam.setOnClickListener {
            val action = WhatWePlayFragmentDirections.actionWhatWePlayFragmentToTeamsFragment()
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.btnAchievement.setOnClickListener {
            val action = WhatWePlayFragmentDirections.actionWhatWePlayFragmentToAchievementFragment()
            Navigation.findNavController(it).navigate(action)
        }
        //holder.binding.cardWhatWePlay.setOnClickListener {
        //}
    }
    fun updateWhatWePlayList(newWhatWePlayList: ArrayList<Game>) {
        whatWePlayList.clear()
        whatWePlayList.addAll(newWhatWePlayList)
        notifyDataSetChanged()
    }

}
