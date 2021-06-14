package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.asDomainModel
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import repository.AppRepository
import java.lang.Exception

class VoterInfoViewModel(val election: Election, application: Application) :AndroidViewModel(application) {
    var client: CivicsApiService = CivicsApi.retrofitService

    private val database = ElectionDatabase.getInstance(application)
    private val appRepository: AppRepository = AppRepository(database)

    private val _selectedElection = MutableLiveData<Election>()


    val selectedElection: LiveData<Election>
        get() = _selectedElection

    val voterInfoForSelectedElection = MutableLiveData<VoterInfo>()

    private val _followButtonText = MutableLiveData<String>()
    val followButtonText: LiveData<String> = _followButtonText

    private suspend fun refreshVoterInfo(electionId: Int, address: String) {

        withContext(Dispatchers.IO) {

            try {
                val networkVoterInfoResponse = client.getVoterInfo(electionId, address)

                val voterInfoDomainModel = networkVoterInfoResponse.asDomainModel()

                voterInfoForSelectedElection.postValue(voterInfoDomainModel)


            } catch (e: Exception) {

                Log.d("ExceptionInRefreshVoterInfo", e.toString())
            }

        }

    }

    val votingLocation: LiveData<String> = Transformations.map(voterInfoForSelectedElection) {
        it?.state?.electionAdministrationBody?.votingLocationFinderUrl
                ?: ""
    }
    val ballotInfoUrl: LiveData<String> = Transformations.map(voterInfoForSelectedElection) {
        it?.state?.electionAdministrationBody?.ballotInfoUrl
                ?: ""
    }

    val mailingAddress: LiveData<String> = Transformations.map(voterInfoForSelectedElection) {
        it?.state?.electionAdministrationBody?.correspondenceAddress?.toFormattedString()
                ?: "No address provided"
    }

    private suspend fun updateFollowButtonText() = withContext(Dispatchers.IO) {
        val text = if (appRepository.isElectionFollowed(election.id)) {
            "Unfollow"
        } else {
            "Follow"
        }

        _followButtonText.postValue(text)
    }

    fun followOrUnfollow() = viewModelScope.launch(Dispatchers.IO) {
        if (appRepository.isElectionFollowed(election.id)) {
            appRepository.unfollowElection(election.id)
            updateFollowButtonText()
        } else {
            appRepository.followElection(election.id)
            updateFollowButtonText()
        }

    }


    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        _selectedElection.value = election

        viewModelScope.launch {
            updateFollowButtonText()

            selectedElection.value?.let {

                refreshVoterInfo(it.id, it.division.country + "/" + it.division.state)
            }

        }

    }


    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    /**
     * Simple ViewModel factory that provides the Eelction and context to the ViewModel.
     */
    class Factory(
            private val election: Election,
            private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
                return VoterInfoViewModel(election, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    //
    // TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}