package com.tsu.itindr.request.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponses(
    val userId: String,
    val name: String?=null,
    val aboutMyself: String?=null,
    val avatar: String?=null,
    val topics:List<TopicResponse>
)