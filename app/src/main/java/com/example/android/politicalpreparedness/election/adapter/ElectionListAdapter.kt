package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.ItemElectionBinding

import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener): ListAdapter<Election,  ElectionViewHolder>(ElectionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListener,item)
    }

   // override fun getLayoutRes(viewType: Int) = R.layout.item_election
    //TODO: Bind ViewHolder

    //TODO: Add companion object to inflate ViewHolder (from)


    companion object ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
        override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
class ElectionViewHolder(private val binding: ItemElectionBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(clickListener: ElectionListener, item: Election) {
        binding.election = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemElectionBinding.inflate(layoutInflater, parent, false)

            return ElectionViewHolder(binding)
        }
    }
}

//TODO: Create ElectionViewHolder

//TODO: Create ElectionDiffCallback

//TODO: Create ElectionListener
class ElectionListener(val clickListener: (election: Election) -> Unit) {
    fun onClick(election: Election) = clickListener(election)
}