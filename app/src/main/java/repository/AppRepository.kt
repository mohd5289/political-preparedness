package repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.politicalpreparedness.database.ElectionDatabase

import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.asDatabaseModel
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.FollowedElection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AppRepository(private val database: ElectionDatabase) {

    var client: CivicsApiService = CivicsApi.retrofitService

    var allElections: List<Election> = database.electionDao.getElections()

    var followedElections: LiveData<List<Election>> = database.electionDao.getFollowedElections()


    suspend fun isElectionFollowed(id: Int) = database.electionDao.getFollowedElection(id) != null && database.electionDao.getFollowedElection(id) == id
    suspend fun followElection(id: Int) = database.electionDao.followElection(FollowedElection(id))
    suspend fun unfollowElection(id: Int) = database.electionDao.unfollowElection(id)

    suspend fun refreshElectionData() {

        refreshElections()

    }


    private suspend fun refreshElections() {
        withContext(Dispatchers.IO) {

            try {
                val networkElectionResponse = client.getElections()

                val networkElectionsList = networkElectionResponse.elections

                val databaseElectionsList = networkElectionsList.asDatabaseModel()

                database.electionDao.insertAllElections(*databaseElectionsList)

            } catch (e: Exception) {
                Log.d("ExceptionInRepo", e.toString())
            }


        }
    }


}