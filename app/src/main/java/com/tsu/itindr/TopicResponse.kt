package com.tsu.itindr

import kotlinx.serialization.Serializable

@Serializable
data class TopicResponse(
    val id:String,
    val title:String
)