package com.example.project_anmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.project_anmp.R
import com.example.project_anmp.databinding.FragmentTeamsBinding
import com.example.project_anmp.databinding.FragmentTeamsDetailBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class TeamsDetailFragment : Fragment() {
    private lateinit var binding: FragmentTeamsDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamsDetailBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedTeam = TeamsDetailFragmentArgs.fromBundle(requireArguments()).selectedTeam
        binding.btnTeamGroup.text = "Team " + selectedTeam

        val selectedGame = TeamsDetailFragmentArgs.fromBundle(requireArguments()).selectedGame

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

        binding.btnTeamGroup.setOnClickListener{
            val action = TeamsDetailFragmentDirections.actionTeamsDetailFragmentToTeamsFragment(selectedGame)
            Navigation.findNavController(it).navigate(action)
        }
    }
}