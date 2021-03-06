package com.tsu.itindr.find.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tsu.itindr.data.chat.GetChatController
import com.tsu.itindr.R
import com.tsu.itindr.chat.ChatActivity
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.databinding.FragmentChatBinding
import com.tsu.itindr.find.FindActivity
import com.tsu.itindr.find.Profile
import com.tsu.itindr.find.chat.model.ProfileItem
import com.tsu.itindr.room.chat.Chat

class ChatFragment : Fragment(R.layout.fragment_chat) {
    companion object {
        val TAG = ChatFragment::class.java.simpleName
        fun newInstance() = ChatFragment()
    }

    private lateinit var binding: FragmentChatBinding
    private val chatAdapterListener = object : ChatAdapter.ChatAdapterListener {
        override fun onItemClick(item: Chat) {

            val intent =
                Intent(activity, ChatActivity::class.java)
            intent.putExtra("chatID", item.id)
            startActivity(intent)
        }
    }
    private val viewModel by lazy {
        ViewModelProvider(this).get(ChatViewModel::class.java)
    }
    private val chatAdapter = ChatAdapter(chatAdapterListener)

    private fun initView() = with(binding) {
        chatRecycler.apply {
            adapter = chatAdapter
            addItemDecoration(ChatItemDecoration())
        }
        viewModel.chats.observe(viewLifecycleOwner) { chatItem ->
            if (chatItem != null) {
                chatAdapter.submitList(chatItem)
            }
        }
        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(activity, "Ошибка чата", Toast.LENGTH_LONG).show()
            }
        }
    }

    val profile: MutableList<Profile> = mutableListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChatBinding.bind(view)
        initView()
        viewModel.getChat()

    }
}
