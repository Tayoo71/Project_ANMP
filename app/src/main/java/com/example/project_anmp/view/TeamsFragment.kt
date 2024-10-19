package com.example.project_anmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.project_anmp.databinding.FragmentTeamsBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class TeamsFragment : Fragment() {
private lateinit var binding:FragmentTeamsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamsBinding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.btnTeamA.setOnClickListener {
            selectedTeam = "A"
            val action = TeamsFragmentDirections.actionTeamsFragmentToTeamsDetailFragment(selectedGame, selectedTeam)
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnTeamB.setOnClickListener {
            selectedTeam = "B"
            val action = TeamsFragmentDirections.actionTeamsFragmentToTeamsDetailFragment(selectedGame, selectedTeam)
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnTeamC.setOnClickListener {
            selectedTeam = "C"
            val action = TeamsFragmentDirections.actionTeamsFragmentToTeamsDetailFragment(selectedGame, selectedTeam)
            Navigation.findNavController(it).navigate(action)
        }
    }
}