package com.example.project_anmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_anmp.databinding.ApplyTeamListItemBinding
import com.example.project_anmp.model.Proposal

class ApplyTeamListAdapter(val applyTeamList:ArrayList<Proposal>)
    : RecyclerView.Adapter<ApplyTeamListAdapter.ApplyTeamViewHolder>() {
        class ApplyTeamViewHolder(var binding: ApplyTeamListItemBinding)
            : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyTeamViewHolder {
        var binding = ApplyTeamListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplyTeamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return applyTeamList.size
    }

    override fun onBindViewHolder(holder: ApplyTeamViewHolder, position: Int) {
        // initialize attribute binding
        holder.binding.proposal = applyTeamList[position]
    }

    fun updateProposalList(newProposalList: List<Proposal>) {
        applyTeamList.clear()
        applyTeamList.addAll(newProposalList)
        notifyDataSetChanged()
    }

}