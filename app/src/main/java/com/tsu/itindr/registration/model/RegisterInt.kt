package com.tsu.itindr.registration.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterInt {

    @POST ("./v1/auth/register")
    fun registerProfile(@Body registerParams: RegisterParams): Call<RegisterResponse>
}