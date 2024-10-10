package com.example.project_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.project_anmp.databinding.FragmentWhatWePlayBinding

class WhatWePlayFragment : Fragment() {
private lateinit var binding:FragmentWhatWePlayBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhatWePlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAchievement.setOnClickListener {
            val action = WhatWePlayFragmentDirections.actionWhatWePlayFragmentToAchievementFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.btnTeams.setOnClickListener {
            val action = WhatWePlayFragmentDirections.actionWhatWePlayFragmentToTeamsFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}