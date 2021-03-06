package com.tsu.itindr.data.user

import com.tsu.itindr.data.profile.LikeResponse
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.data.profile.UpdateParams
import retrofit2.Call
import retrofit2.http.*

interface UserInt {
    @PATCH("./v1/profile")
    fun updateProfile(@Header("Authorization")token:String,@Body updateParams: UpdateParams): Call<ProfileResponses>

    @GET("./v1/user/feed")
    fun feedUser(@Header("Authorization")token:String):Call<List<ProfileResponses>>

    @POST()
    fun like(@Header("Authorization")token:String,@Url url:String):Call<LikeResponse>

    @POST()
    fun disLike(@Header("Authorization")token:String,@Url url:String):Call<String>

    @GET("./v1/user")
    fun getPeople(@Header("Authorization")token:String,@Query("limit") limit:Int,@Query("offset") offset:Int):Call<List<ProfileResponses>>
}