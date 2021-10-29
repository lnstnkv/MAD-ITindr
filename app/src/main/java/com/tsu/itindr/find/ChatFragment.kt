package com.tsu.itindr.find

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tsu.itindr.GetChatController
import com.tsu.itindr.R
import com.tsu.itindr.SharedPreference
import com.tsu.itindr.databinding.FragmentChatBinding
import com.tsu.itindr.databinding.FragmentProfileBinding
import com.tsu.itindr.edit.EditActivity

class ChatFragment:Fragment(R.layout.fragment_chat) {
    companion object{
        val TAG = ChatFragment::class.java.simpleName
        fun newInstance()= ChatFragment()
    }
    private lateinit var binding: FragmentChatBinding
    private var controller=GetChatController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = SharedPreference(activity as FindActivity)
        binding = FragmentChatBinding.bind(view)
        controller.getChat(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {

                Toast.makeText(activity, "Работает", Toast.LENGTH_LONG).show()
            },
            onFailure = {
                Toast.makeText(activity, "ТЫ сделала хрень", Toast.LENGTH_LONG).show()
            }

        )
    }
}