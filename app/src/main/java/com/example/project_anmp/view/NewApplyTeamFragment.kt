package com.example.project_anmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.project_anmp.R
import com.example.project_anmp.databinding.FragmentNewApplyTeamBinding
import com.example.project_anmp.viewmodel.NewApplyTeamViewModel
import com.google.android.material.textfield.TextInputEditText

class NewApplyTeamFragment : Fragment(), NewApplyTeamActionsHandler {
    private lateinit var binding: FragmentNewApplyTeamBinding
    private lateinit var viewModel: NewApplyTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewApplyTeamBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Data Binding
        viewModel = ViewModelProvider(this).get(NewApplyTeamViewModel::class.java)
        binding.handler = this

        viewModel.fetchGames()
        observeViewModel()

        // Handle spinnerGame selection
        binding.spinnerGame.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Reset teamsDataLD to default state when "Choose Game" is selected or changed
                viewModel.teamsDataLD.postValue(listOf())

                if (position > 0) {
                    val selectedGame = viewModel.gamesDataLD.value?.get(position - 1) // Skip "Choose Game"
                    selectedGame?.let {
                        viewModel.fetchTeamsForGame(it)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun observeViewModel(){
        viewModel.statusMessage.observe(viewLifecycleOwner, Observer {
            if(it == "Proposal Apply Team Successfully Added!"){
                findNavController().popBackStack()
            }

            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        // Update the adapter for spinnerGame dynamically
        viewModel.gamesDataLD.observe(viewLifecycleOwner, Observer { games ->
            val updatedGames = mutableListOf("Choose Game").apply { addAll(games) }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, updatedGames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerGame.adapter = adapter
        })

        // Update the adapter for spinnerTeam dynamically
        viewModel.teamsDataLD.observe(viewLifecycleOwner, Observer { teams ->
            val updatedTeams = mutableListOf("Choose Team").apply { addAll(teams) }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, updatedTeams)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerTeam.adapter = adapter
        })
    }

    override fun onSubmitClicked(spinnerGame: Spinner, spinnerTeam: Spinner, description: String) {
        val game = spinnerGame.selectedItem?.toString() ?: "Choose Game"
        val team = spinnerTeam.selectedItem?.toString() ?: "Choose Team"

        viewModel.applyTeam(game, team, description)
    }
}