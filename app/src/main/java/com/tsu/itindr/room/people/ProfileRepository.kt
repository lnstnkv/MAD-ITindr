package com.tsu.itindr.room.people

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.tsu.itindr.find.people.model.PeopleProfile
import com.tsu.itindr.room.Database

class ProfileRepository(context: Context) {
    private val profileDao = Database.getInstance(context).getProfileDao()

    fun observeAllProfiles(): LiveData<List<PeopleProfile>> =
        profileDao.observeAll().map { list -> list.map { it.toDomain() } }


    suspend fun addNew(id: String, name: String, avatar: String) {
        profileDao.addProfile(
            ProfileEntity(
                id = id,
                name = name,
                avatar = avatar
            )
        )
    }
}

