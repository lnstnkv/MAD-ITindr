package com.tsu.itindr.request.register

import com.tsu.itindr.request.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterController {

    private val api: RegisterInt = Network.retrofit.create(RegisterInt::class.java)

    fun register(registerParams: RegisterParams, onSuccess: (data: RegisterResponse) -> Unit, onFailure: () -> Unit) {
        api.registerProfile(registerParams).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let{
                    onSuccess.invoke(it)
                    }
                } else {
                    onFailure.invoke()
                    //Log.i(TAG, "Ошибка тут")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onFailure.invoke()
               // Log.i(TAG, "нет Ошибка тут")
            }

        })
    }
}