package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.FollowedElection

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElection(election: Election)


    //TODO: Add select all election query
    @Query("SELECT * FROM election_table")
     fun getElections():  List<Election>

    //TODO: Add select single election query
    @Query("SELECT * from election_table where id = :id")
    suspend fun getElectionById(id: Int): Election?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllElections(vararg elections: Election)

    //TODO: Add delete query
    @Query("DELETE FROM election_table where id=:id")
    suspend fun deleteById(id: Int)
    //TODO: Add clear query

    @Query("DELETE FROM election_table")
    suspend fun clearAll()
    @Query("SELECT * FROM election_table INNER JOIN followed_elections ON election_table.id = followed_elections.id ")
    fun getFollowedElections():LiveData<List<Election>>

    @Query("SELECT election_table.id FROM election_table WHERE id =:id AND  id IN followed_elections ")
    suspend fun getFollowedElection(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun followElection(id: FollowedElection)

    @Query("DELETE FROM followed_elections WHERE id = :id")
    suspend fun unfollowElection(id: Int)

}