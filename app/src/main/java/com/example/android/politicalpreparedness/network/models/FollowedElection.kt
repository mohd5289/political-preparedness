package com.example.android.politicalpreparedness.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followed_elections")
data class FollowedElection constructor(
        @PrimaryKey
        val id: Int
)
