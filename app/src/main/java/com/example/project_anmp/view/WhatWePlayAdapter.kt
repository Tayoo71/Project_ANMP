package com.example.project_anmp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.project_anmp.databinding.WwpListItemBinding
import com.example.project_anmp.model.GameData
import com.squareup.picasso.Picasso

class WhatWePlayAdapter(val whatWePlayList: ArrayList<GameData>) :
    RecyclerView.Adapter<WhatWePlayAdapter.WhatWePlayViewHolder>(), WhatWePlayListActionHandler {

    class WhatWePlayViewHolder(var binding: WwpListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhatWePlayViewHolder {
        val binding = WwpListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WhatWePlayViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return whatWePlayList.size
    }

    override fun onBindViewHolder(holder: WhatWePlayViewHolder, position: Int) {
        holder.binding.listener = this
        holder.binding.game = whatWePlayList[position]

        //holder.binding.cardWhatWePlay.setOnClickListener {
        //}
    }
    fun updateWhatWePlayList(newWhatWePlayList: List<GameData>) {
        whatWePlayList.clear()
        whatWePlayList.addAll(newWhatWePlayList)
        notifyDataSetChanged()
    }

    override fun onButtonAchievementClicked(v: View, game: GameData) {
        val action = WhatWePlayFragmentDirections.actionWhatWePlayFragmentToAchievementFragment(game)
        Navigation.findNavController(v).navigate(action)
    }

    override fun onButtonTeamClicked(v: View, game: GameData) {
        val action = WhatWePlayFragmentDirections.actionWhatWePlayFragmentToTeamsFragment(game)
        Navigation.findNavController(v).navigate(action)
    }
}
