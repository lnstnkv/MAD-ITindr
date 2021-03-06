package com.tsu.itindr.data.profile

import kotlinx.serialization.Serializable

@Serializable
data class UpdateParams(
    val name: String? = null,
    val aboutMyself: String? = null,
    val topics: List<String>
)