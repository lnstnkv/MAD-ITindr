package com.tsu.itindr.request.profile

import kotlinx.serialization.Serializable

@Serializable
data class TopicResponse(
    val id:String,
    val title:String
)