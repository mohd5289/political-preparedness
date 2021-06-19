package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.room.Entity

import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*
/*
 *Domain level models
 *
 * */

//------------------------------------ Election -----------------------------//




//------------------------------------ Voter Info -----------------------------//
data class VoterInfo (
        val electionId: Int,
        //val pollingLocations: String? = null, //
        // TODO: Future Use
        // val contests: String? = null, //TODO: Future Use
        val state: State
        //val electionElectionOfficials: List<NetworkElectionOfficial>? = null
)








//------------------------------------ Representative-----------------------------//



data class Representative (
        val official: Official,
        val office: Office
)






