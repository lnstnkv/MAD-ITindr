package com.tsu.itindr.edit

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.avatar.AvatarController
import com.tsu.itindr.data.profile.*
import com.tsu.itindr.data.user.UserController
import com.tsu.itindr.room.people.ProfileRepository
import com.tsu.itindr.room.topic.TopicRepository
import kotlinx.coroutines.launch

class EditViewModel(app: Application) : AndroidViewModel(app) {

    val sharedPreference = SharedPreference(app)
    private val updateController = UserController(app)
    private val saveAvatar = AvatarController(app)
    private val controllerTopic = TopicController(app)
    private var controller = ProfileController(app)

    private val _isErrorFromTopic = MutableLiveData<Boolean>()
    val isErrorFromTopic: LiveData<Boolean>
        get() = _isErrorFromTopic

    private val _isTopic = MutableLiveData<List<TopicResponse>?>()
    val isTopic: LiveData<List<TopicResponse>?>
        get() = _isTopic

    private val _isErrorAvatar = MutableLiveData<Boolean>()
    val isErrorAvatar: LiveData<Boolean>
        get() = _isErrorAvatar

    private val _isErrorProfile = MutableLiveData<ProfileResponses?>()
    val isErrorProfile: LiveData<ProfileResponses?>
        get() = _isErrorProfile

    private val _isErrorUpdateProfile = MutableLiveData<Boolean>()
    val isErrorUpdateProfile: LiveData<Boolean>
        get() = _isErrorUpdateProfile

    private val _isErrorSaveAvatar = MutableLiveData<Boolean>()
    val isErrorSaveAvatar: LiveData<Boolean>
        get() = _isErrorSaveAvatar

    private val topicRepository = TopicRepository(app)
    val topics = topicRepository.observeAllProfiles()


    val accessToken = sharedPreference.getValueString("accessToken")

    fun deleteAvatar() {


        saveAvatar.deleteAvatar(
            onSuccess = {
                _isErrorAvatar.value = false
            },
            onFailure = {
                _isErrorAvatar.value = true
            })

    }

    fun addTopic() {

        controllerTopic.topic(
            onSuccess = {
                _isErrorFromTopic.value = false
                _isTopic.value = it
                add(it)

            },
            onFailure = {
                _isErrorFromTopic.value = true
                _isTopic.value = null
            }

        )

    }

    fun saveAvatar(uri: Uri) {

        saveAvatar.updateAvatar(
            uri,
            onSuccess = {
                _isErrorSaveAvatar.value = false

            },
            onFailure = {
                _isErrorSaveAvatar.value = true

            })

    }


    fun getProfile() {
        controller.profile(
            onSuccess = {
                _isErrorProfile.value = it

            },
            onFailure = {
                _isErrorProfile.value = null
            }
        )
    }

    fun updateProfile(name: String, aboutMyself: String, topics: List<String>) {

        updateController.update(
            UpdateParams(
                name,
                aboutMyself,
                topics
            ),
            onSuccess = {
                _isErrorUpdateProfile.value = false

            },
            onFailure = {
                _isErrorUpdateProfile.value = true

            }
        )
    }

    fun add(topicsItem: List<TopicResponse>) {
        for (topic in topicsItem) {
            viewModelScope.launch {
                topicRepository.addNew(
                    id = topic.id,
                    title = topic.title
                )
            }
        }
    }
}