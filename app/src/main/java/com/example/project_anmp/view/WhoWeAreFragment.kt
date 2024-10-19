package com.example.project_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.project_anmp.R

class WhoWeAreFragment : Fragment() {

    private var likeCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_who_we_are, container, false)

        val likeButton: Button = view.findViewById(R.id.button)
        val likeTextView: TextView = view.findViewById(R.id.textView3)

        likeButton.setOnClickListener {
            likeCount++
            likeTextView.text = likeCount.toString()
        }

        return view
    }
}
