package com.example.project_anmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.project_anmp.databinding.FragmentScheduleDetailBinding
import com.example.project_anmp.model.Schedule
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ScheduleDetailFragment : Fragment() {
    private lateinit var binding: FragmentScheduleDetailBinding
    private lateinit var schedule: Schedule

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedSchedule = ScheduleDetailFragmentArgs.fromBundle(requireArguments()).selectedSchedule
        binding.txtEventName.text =selectedSchedule.event
        binding.txtLoc.text = selectedSchedule.location + formatTime(selectedSchedule.datetime)
        binding.txtTeam.text = selectedSchedule.team + " - " + selectedSchedule.game
        binding.txtDescription.text = selectedSchedule.description

        // Load Gambar using Picasso
        val picasso = Picasso.Builder(binding.root.context)
        picasso.listener{picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(selectedSchedule.venue_photo).into(binding.imgProfile,
            object: Callback {
                override fun onSuccess() {
                    binding.progressImage.visibility = View.INVISIBLE
                    binding.imgProfile.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString())
                }
            })


        // Handle btnNotify click
        binding.btnNotify.setOnClickListener {
            if (binding.btnNotify.text == "Disable Notification") {
                // Membatalkan notifikasi dan mengubah teks tombol menjadi "Notify Me"
                Toast.makeText(
                    context,
                    "Notification has been canceled. You will no longer receive reminders for this schedule.",
                    Toast.LENGTH_LONG
                ).show()

                binding.btnNotify.text = "Notify Me"
            } else {
                // Mengaktifkan notifikasi dan mengubah teks tombol menjadi "Disable Notification"
                Toast.makeText(
                    context,
                    "You will be notified when the schedule starts. Notifications have been successfully enabled.",
                    Toast.LENGTH_LONG
                ).show()

                binding.btnNotify.text = "Disable Notification"
            }
        }
    }

    fun formatTime(datetime: String): String {
        // Parsing datetime dari string menjadi LocalDateTime
        val parsedDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        // Membuat formatter untuk mengambil waktu dalam format 12-jam (AM/PM)
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

        // Mengambil waktu dan memformatnya
        val time = parsedDateTime.format(timeFormatter)

        // Mengembalikan waktu yang diformat
        return " (" + time + ")"
    }

}