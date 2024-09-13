package com.example.project_anmp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
}