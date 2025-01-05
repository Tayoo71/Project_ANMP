package com.example.project_anmp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.project_anmp.R
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_anmp.databinding.FragmentOurScheduleBinding
import com.example.project_anmp.databinding.FragmentScheduleDetailBinding
import com.example.project_anmp.databinding.FragmentWhoWeAreBinding
import com.example.project_anmp.model.AppDatabase
import com.example.project_anmp.model.LikeDao
import com.example.project_anmp.model.LikeData
import com.example.project_anmp.viewmodel.OurScheduleViewModel
import com.example.project_anmp.viewmodel.ScheduleDetailViewModel
import com.example.project_anmp.viewmodel.WhoWeAreViewModel

class WhoWeAreFragment : Fragment() {

    private lateinit var binding: FragmentWhoWeAreBinding
    private lateinit var viewModel: WhoWeAreViewModel
    private var likeCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhoWeAreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this).get(WhoWeAreViewModel::class.java)
        viewModel.refresh()

        val sharedPreferences = requireContext().getSharedPreferences("com.example.project_anmp", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)

        observeViewModel()

        binding.button.setOnClickListener{
            viewModel.getLikeStatus(username.toString())
        }
    }

    fun observeViewModel(){
        // Mengamati LiveData yang berisi data like
        viewModel.likeCountLD.observe(viewLifecycleOwner) { LikeData ->
            likeCount = LikeData.like
            binding.textView3.text = likeCount.toString()
        }

        viewModel.likeStatusLD.observe(viewLifecycleOwner) { status ->
            val username = requireContext().getSharedPreferences(
                "com.example.project_anmp",
                Context.MODE_PRIVATE
            )
                .getString("username", null)

            if (status == false) {
                viewModel.increaseLike(username.toString())
            } else {
                viewModel.decreaseLike(username.toString())
            }
        }
    }
}
