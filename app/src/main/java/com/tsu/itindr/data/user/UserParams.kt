package com.tsu.itindr.data.user

import kotlinx.serialization.Serializable

@Serializable
data class UserParams(
    val userId: String,
    val name:String,
    val aboutMyself:String?=null,
    val avatar:String?=null
)
