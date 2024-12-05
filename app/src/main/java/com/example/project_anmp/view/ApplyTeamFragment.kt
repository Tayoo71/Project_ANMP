package com.example.project_anmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_anmp.R
import com.example.project_anmp.databinding.FragmentApplyTeamBinding
import com.example.project_anmp.viewmodel.ListApplyTeamViewModel

class ApplyTeamFragment : Fragment(), ApplyTeamActionsHandler {
    private lateinit var binding:FragmentApplyTeamBinding
    private lateinit var viewModel:ListApplyTeamViewModel
    private val applyTeamListAdapter = ApplyTeamListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApplyTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.handler = this
        viewModel = ViewModelProvider(this).get(ListApplyTeamViewModel::class.java)
        viewModel.refresh()
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = applyTeamListAdapter

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.proposalLD.observe(viewLifecycleOwner, Observer{
            applyTeamListAdapter.updateProposalList(it)
            if(it.isEmpty()) {
                binding.recView?.visibility = View.GONE
                binding.txtError?.visibility = View.VISIBLE
            } else {
                binding.recView?.visibility = View.VISIBLE
                binding.txtError?.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.progressLoad?.visibility = View.GONE
            } else {
                binding.progressLoad?.visibility = View.VISIBLE
            }
        })

        viewModel.proposalLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.txtError?.visibility = View.GONE
            } else {
                binding.txtError?.visibility = View.VISIBLE
            }
        })
    }

    override fun onFabAddClicked(v: View) {
        val action = ApplyTeamFragmentDirections.actionNewApplyTeam()
        Navigation.findNavController(v).navigate(action)
    }

    override fun onRefresh() {
        binding.recView.visibility = View.GONE
        binding.txtError.visibility = View.GONE
        binding.progressLoad.visibility = View.VISIBLE
        viewModel.refresh()
        binding.refreshLayout.isRefreshing = false
    }

}