package com.example.project_anmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.project_anmp.R
import com.example.project_anmp.databinding.FragmentTeamsBinding
import com.example.project_anmp.model.Team
import com.example.project_anmp.viewmodel.SignUpViewModel
import com.example.project_anmp.viewmodel.TeamsViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class TeamsFragment : Fragment() {

    private lateinit var binding: FragmentTeamsBinding
    private lateinit var viewModel: TeamsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamsBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TeamsViewModel::class.java)

        var selectedTeam = ""

        val selectedGame = TeamsFragmentArgs.fromBundle(requireArguments()).selectedGame

        val picasso = Picasso.Builder(binding.root.context)
        picasso.listener{picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(selectedGame.urlLink).into(binding.imgGame,
            object: Callback {
                override fun onSuccess() {
                    binding.progressImageTeam.visibility = View.INVISIBLE
                    binding.imgGame.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString())
                }
            })


        // Load teams for the selected game
        viewModel.refresh(selectedGame.name)

        viewModel.teamsLD.observe(viewLifecycleOwner) { teams ->
            // Dynamically create buttons for the filtered teams
            createButtons(teams, binding.dynamicButtonContainer)
        }
    }

    private fun createButtons(teams: List<String>, container: LinearLayout) {
        container.removeAllViews() // Clear existing buttons
        teams.forEach { team ->
            val button = Button(requireContext()).apply {
                text = team
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 8, 8, 8)
                }
            }

            // Set the click listener for each button
            button.setOnClickListener {
                // Set the selected team
                val selectedTeam = team

                // Get the selected game from the arguments (already retrieved as 'selectedGame' earlier)
                val selectedGame = TeamsFragmentArgs.fromBundle(requireArguments()).selectedGame

                // Create the navigation action to the TeamsDetailFragment
                val action = TeamsFragmentDirections.actionTeamsFragmentToTeamsDetailFragment(selectedTeam, selectedGame)

                // Perform the navigation
                Navigation.findNavController(it).navigate(action)
            }

            container.addView(button)
        }
    }
}