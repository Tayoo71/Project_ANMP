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
import com.example.project_anmp.view.OurScheduleListAdapter.OurScheduleViewHolder
import com.example.project_anmp.viewmodel.AchievementViewModel
import com.example.project_anmp.viewmodel.OurScheduleViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class AchievementFragment : Fragment() {
    private lateinit var binding: FragmentAchievementBinding
    private lateinit var viewModel: AchievementViewModel
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

        var i = 0

        val achievementList = arrayListOf<Achievement>()
        val selectedGame= AchievementFragmentArgs.fromBundle(requireArguments()).selectedGame

        binding.txtGameAchievement.text = selectedGame.name

        var achievement : MutableList <String> = ArrayList()
        var achievementYear : MutableList <String> = ArrayList()

        achievementYear.add("All")

        for (x in 0 .. achievementList.count()-1){
            if(selectedGame.name == achievementList[x].gameName){
                if (achievementYear.count()!=1){
                    if(!achievementYear.contains(achievementList[x].year)){
                        achievementYear.add(achievementList[x].year)
                    }
                }
                else {
                    achievementYear.add(achievementList[x].year)
                }
                achievement.add(achievementList[x].achievementDescription)
            }
        }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, achievementYear)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAchievement.adapter = spinnerAdapter

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
        binding.spinnerAchievement.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if(selectedItem == "All"){
                    for (x in 0 .. achievement.count()-1){
                            textAdd.plus(binding.txtAchievement.text)
                            textAdd.plus(achievement[x])
                            textAdd.plus(achieveme)
                        }
                    }
                }
            }
        }
    }
}