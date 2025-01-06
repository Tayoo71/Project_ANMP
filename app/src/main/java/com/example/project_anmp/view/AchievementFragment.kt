package com.example.project_anmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_anmp.R
import com.example.project_anmp.databinding.FragmentAchievementBinding
import com.example.project_anmp.databinding.FragmentTeamsDetailBinding
import com.example.project_anmp.model.Achievement
import com.example.project_anmp.model.AchievementData
import com.example.project_anmp.model.Game
import com.example.project_anmp.model.GameData
import com.example.project_anmp.view.OurScheduleListAdapter.OurScheduleViewHolder
import com.example.project_anmp.viewmodel.AchievementViewModel
import com.example.project_anmp.viewmodel.OurScheduleViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class AchievementFragment : Fragment() {
    private lateinit var binding: FragmentAchievementBinding
    private lateinit var viewModel: AchievementViewModel
    // Menggunakan Mutable List untuk modifikasi datanya
    var achievementYear : MutableList <String> = ArrayList()
    var achievementDescriptions : MutableList <String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAchievementBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this).get(AchievementViewModel::class.java)
        viewModel.refresh()

        //var i = 0

        //val achievementList = arrayListOf<Achievement>()
        val selectedGame= AchievementFragmentArgs.fromBundle(requireArguments()).selectedGame

        // Mengamati LiveData yang berisi daftar achievement
        viewModel.achievementLD.observe(viewLifecycleOwner) { achievementList ->
            updateAchievementList(achievementList, selectedGame)
        }

        viewModel.achievementLD.observe(viewLifecycleOwner) { achievementList ->
            if (achievementList.isNotEmpty()) {
                binding.txtGameAchievement.text = selectedGame.name
            }
        }

        binding.txtGameAchievement.text = selectedGame.name



        val picasso = Picasso.Builder(binding.root.context)
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(selectedGame.urlLink).into(binding.imgGameAchievement,
            object : Callback {
                override fun onSuccess() {
                    binding.progressImageAchievement.visibility = View.INVISIBLE
                    binding.imgGameAchievement.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString())
                }
            })

        var textAdd= ""
        binding.spinnerAchievement.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var textAdd = "" // Reset textAdd untuk memulai yang baru
                var int = 0
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem == "All") {
                    int = 0
                    for (x in 0 until achievementDescriptions.count()) {
                        textAdd += "${int + 1}. ${achievementDescriptions[x]}\n"
                        int += 1
                    }
                } else {
                    int = 0
                    for (x in 0 until achievementDescriptions.count()) {
                        if (achievementDescriptions[x].contains(selectedItem)) {
                            textAdd += "${int + 1}. ${achievementDescriptions[x]}\n"
                            int += 1
                        }
                    }
                }
                binding.txtAchievement.text = textAdd
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Kosongkan text jika tidak ada yang dipilih
                binding.txtAchievement.text = ""
            }
        }
    }

    private fun updateAchievementList(achievementList: List<AchievementData>, selectedGame: GameData) {
        achievementYear = mutableListOf("All")
        achievementDescriptions = mutableListOf<String>()

        for (achievement in achievementList) {
            if (selectedGame.name == achievement.gameName) {
                if (!achievementYear.contains(achievement.year)) {
                    achievementYear.add(achievement.year)
                }
                achievementDescriptions.add(achievement.achievementDescription)
            }
        }

        // Perbarui spinner dengan tahun-tahun yang relevan
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, achievementYear)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAchievement.adapter = spinnerAdapter

        // Simpan deskripsi pencapaian untuk digunakan nanti
        binding.spinnerAchievement.tag = achievementDescriptions

        //binding.txtGameAchievement.text = achievementDescriptions[0]
    }
}