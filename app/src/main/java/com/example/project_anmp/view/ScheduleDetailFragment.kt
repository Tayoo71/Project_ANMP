package com.example.project_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.project_anmp.databinding.FragmentScheduleDetailBinding
import com.example.project_anmp.viewmodel.ScheduleDetailViewModel


class ScheduleDetailFragment : Fragment(), ScheduleDetailActionsHandler {
    private lateinit var binding: FragmentScheduleDetailBinding
    private lateinit var viewModel: ScheduleDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ScheduleDetailViewModel::class.java)
        viewModel.refresh(ScheduleDetailFragmentArgs.fromBundle(requireArguments()).selectedSchedule)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.listener = this

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.scheduleDetailLD.observe(viewLifecycleOwner, Observer {
            binding.schedule = it
        })
    }

    override fun buttonNotifyClicked(v: View) {
        if(v is Button) {
            viewModel.toggleNotification()
            if (v.text.toString() == "Disable Notification") {
                Toast.makeText(
                    context,
                    "Notification has been canceled. You will no longer receive reminders for this schedule.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                Toast.makeText(
                    context,
                    "You will be notified when the schedule starts. Notifications have been successfully enabled.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}