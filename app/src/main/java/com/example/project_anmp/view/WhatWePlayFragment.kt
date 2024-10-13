package com.example.project_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_anmp.databinding.FragmentWhatWePlayBinding
import com.example.project_anmp.viewmodel.WhatWePlayViewModel

class WhatWePlayFragment : Fragment() {
    private lateinit var binding: FragmentWhatWePlayBinding
    private lateinit var viewModel: WhatWePlayViewModel
    private val whatWePlayAdapter = WhatWePlayAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhatWePlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(WhatWePlayViewModel::class.java)
        viewModel.refresh()

        // Setup RecyclerView
        binding.recViewGame.layoutManager = LinearLayoutManager(context)
        binding.recViewGame.adapter = whatWePlayAdapter

        observeViewModel()

        // Refresh layout handling
        binding.refreshLayout.setOnRefreshListener {
            binding.recViewGame.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        // Observe LiveData for the list of games
        viewModel.whatWePlayLD.observe(viewLifecycleOwner, Observer {
            whatWePlayAdapter.updateWhatWePlayList(it)
            binding.recViewGame.visibility = View.VISIBLE
            binding.refreshLayout.isRefreshing = false
        })

        // Observe errors when loading data
        viewModel.playLoadErrorLD.observe(viewLifecycleOwner, Observer {
            binding.txtError.visibility = if (it == true) View.VISIBLE else View.GONE
        })

        // Observe loading status
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.recViewGame.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recViewGame.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
    }
}
