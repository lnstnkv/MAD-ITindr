package com.tsu.itindr.find.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ItemChatBinding
import com.tsu.itindr.find.chat.model.ProfileItem
import com.tsu.itindr.room.chat.Chat

class ChatAdapter(
    private val listener: ChatAdapterListener
) : ListAdapter<Chat, ChatAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChatBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(profileItem: Chat) = with(binding) {
            textViewNameUser.text = profileItem.title
            profileItem.lastMessage?.let { textViewLasMessenger.text = it }
            profileItem.avatar?.let {
                Glide
                    .with(imageViewUserChat.context)
                    .load(profileItem.avatar)
                    .circleCrop()
                    .into(imageViewUserChat)
                imageViewUserChat.clipToOutline = true
            }
        }
    }

    interface ChatAdapterListener {
        fun onItemClick(item: Chat)
    }
}
