package com.example.project_anmp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.project_anmp.databinding.FragmentOurScheduleBinding


class OurScheduleFragment : Fragment() {
private lateinit var binding:FragmentOurScheduleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOurScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSchedulePage.setOnClickListener {
            val action = OurScheduleFragmentDirections.actionOurScheduleFragmentToScheduleDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }
}