package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {
    private lateinit var viewModel:VoterInfoViewModel
    private lateinit var binding: FragmentVoterInfoBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        //TODO: Add ViewModel values and create ViewModel
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voter_info, container, false)

     //   binding.viewModel = model

       val selectedElection = VoterInfoFragmentArgs.fromBundle(requireArguments()).selectedElection
        val viewModelFactory = VoterInfoViewModel.Factory(selectedElection, application)
       viewModel= ViewModelProvider(
               this, viewModelFactory).get(VoterInfoViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

       // binding.electionName.text = selectedElection.name
        binding.electionDate.text = selectedElection.electionDay.toString()
        //binding.viewModel = viewModel

        viewModel.election = selectedElection

        //viewModel.checkElectionFollowStatus()

        binding.info.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://myvote.wi.gov"))
            startActivity(browserIntent)
        }

        return binding.root
        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

    }

    //TODO: Create method to load URL intents

}