package com.tsu.itindr

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserController {
    private val api: UserInt = Network.retrofit.create(UserInt::class.java)
    fun update(accessToken:String,profileParams: ProfileParams,onSuccess: () -> Unit, onFailure: () -> Unit){
        api.updateProfile(accessToken,profileParams).enqueue(object: Callback<ProfileResponses>{
            override fun onResponse(
                call: Call<ProfileResponses>,
                response: Response<ProfileResponses>
            ) {
                if (response.isSuccessful) {
                onSuccess.invoke()
            } else {
                onFailure.invoke()
            }
        }
            override fun onFailure(call: Call<ProfileResponses>, t: Throwable) {
                onFailure.invoke()
            }
        })
    }
}