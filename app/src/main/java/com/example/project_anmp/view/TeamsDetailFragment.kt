package com.example.project_anmp.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.project_anmp.R
import com.example.project_anmp.databinding.FragmentTeamsBinding
import com.example.project_anmp.databinding.FragmentTeamsDetailBinding
import com.example.project_anmp.model.Team
import com.example.project_anmp.viewmodel.TeamDetailViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class TeamsDetailFragment : Fragment() {
    private lateinit var binding: FragmentTeamsDetailBinding
    private lateinit var viewModel: TeamDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamsDetailBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TeamDetailViewModel::class.java)

        var selectedTeam = TeamsDetailFragmentArgs.fromBundle(requireArguments()).selectedTeam
        val selectedGame = TeamsDetailFragmentArgs.fromBundle(requireArguments()).selectedGame

        if(selectedTeam.contains("Team")){
            binding.btnTeamGroup.text = selectedTeam
        }else{
            binding.btnTeamGroup.text = "Team " + selectedTeam
        }

        val picasso = Picasso.Builder(binding.root.context)
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(selectedGame.urlLink).into(binding.imgGameTeamDetail,
            object : Callback {
                override fun onSuccess() {
                    binding.progressImageTeamDetail.visibility = View.INVISIBLE
                    binding.imgGameTeamDetail.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString())
                }
            })

        // Fetch team members
        viewModel.refresh(selectedGame.name, selectedTeam)

        // Observe the LiveData for team members
        viewModel.teamsLD.observe(viewLifecycleOwner){ teams ->
            createPlayerViews(teams)
        }

        binding.btnTeamGroup.setOnClickListener{
            val action = TeamsDetailFragmentDirections.actionTeamsDetailFragmentToTeamsFragment(selectedGame)
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun createPlayerViews(teams: List<Team>) {
        // Clear any existing player views
        binding.playerContainer.removeAllViews()

        teams.forEach { team ->
            // Create a new LinearLayout for each player
            val playerLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(16, 16, 16, 16)
                }
            }

            // Create ImageView for player profile picture
            val circleImageView = de.hdodenhof.circleimageview.CircleImageView(requireContext()).apply {
                setImageUrl(team.profilePictureUrl)
                setBorderWidth(4.dpToPx(requireContext()))
                setBorderColor(Color.parseColor("red"))
                layoutParams = LinearLayout.LayoutParams(
                    80.dpToPx(requireContext()),
                    80.dpToPx(requireContext())
                ).apply {
                    setMargins(16, 16, 16, 16)
                }
            }

            // Create a new LinearLayout to hold the name and role TextViews
            val textLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_VERTICAL // Vertically center the content
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            // Create TextView for player name
            val nameTextView = TextView(requireContext()).apply {
                text = team.user
                setTextColor(Color.BLACK)
                textSize = 16f
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 8, 8, 8)
                }
            }

            // Create TextView for player role
            val roleTextView = TextView(requireContext()).apply {
                text = "Role: ${team.role}"
                setTextColor(Color.GRAY)
                textSize = 14f
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 8, 8, 8)
                }
            }

            // Add name and role TextViews to the textLayout
            textLayout.addView(nameTextView)
            textLayout.addView(roleTextView)

            // Add the profile image and the textLayout to the playerLayout
            playerLayout.addView(circleImageView)
            playerLayout.addView(textLayout)

            // Add the playerLayout to the container
            binding.playerContainer.addView(playerLayout)
        }
    }

    private fun ImageView.setImageUrl(url: String) {
        Picasso.get().load(url).into(this)
    }

    // Helper extension function to convert dp to px
    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

}