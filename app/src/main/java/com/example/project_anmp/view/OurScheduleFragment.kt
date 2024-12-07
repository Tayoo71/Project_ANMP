package com.example.project_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_anmp.databinding.FragmentOurScheduleBinding
import com.example.project_anmp.viewmodel.OurScheduleViewModel


class OurScheduleFragment : Fragment() {
private lateinit var binding:FragmentOurScheduleBinding
private lateinit var viewModel:OurScheduleViewModel
private val ourScheduleListAdapter = OurScheduleListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOurScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this).get(OurScheduleViewModel::class.java)
        viewModel.refresh()

        // Setup RecyclerView
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = ourScheduleListAdapter

        observeViewModel()

        // Refresh layout handling
        binding.refreshLayout.setOnRefreshListener {
            binding.recView.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
    }

    fun observeViewModel(){
        // Mengamati LiveData yang berisi daftar schedule
        viewModel.ourSchedulesLD.observe(viewLifecycleOwner, Observer {
            ourScheduleListAdapter.updateOurScheduleList(it)
            binding.recView.visibility = View.VISIBLE
            binding.refreshLayout.isRefreshing = false
        })

        // Mengamati error dalam memuat data
        viewModel.scheduleLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.GONE
            }
        })

        // Mengamati status loading
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.recView.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
    }
}