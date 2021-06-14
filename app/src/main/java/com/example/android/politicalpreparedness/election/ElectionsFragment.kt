package com.example.android.politicalpreparedness.election

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.CivicsApi

class ElectionsFragment: Fragment() {

    //TODO: Declare ViewModel
    val viewModel: ElectionsViewModel by viewModels { ElectionsViewModelFactory(activity as Context) }
    private lateinit var binding: FragmentElectionBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_election, container, false)


        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setupRecyclerviewForSavedElection()
    }

    //TODO: Add binding values

    //TODO: Link elections to voter info

    //TODO: Initiate recycler adapters

    //TODO: Populate recycler adapters

    //TODO: Refresh adapters when fragment loads
    private fun setupRecyclerView() {
        val adapter = ElectionListAdapter(ElectionListener {
            viewModel.onElectionClicked(it,this)
        })
        binding.rvUpcomingElections.setup(adapter)

    }

    override fun onResume() {
        super.onResume()
        viewModel.getUpcomingElections()
        viewModel.getSavedElections()
    }
    private fun setupRecyclerviewForSavedElection() {
        val adapter = ElectionListAdapter (ElectionListener {   viewModel.onElectionClicked(it,this) })
        binding.rvSavedElections.setup(adapter)
    }


}

private fun RecyclerView.setup(adapter: ElectionListAdapter) {

    this.apply {
        layoutManager = LinearLayoutManager(this.context)
        this.adapter = adapter
    }

}

