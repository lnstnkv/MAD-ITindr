package com.tsu.itindr.tellabout

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
import com.tsu.itindr.room.topic.TopicRepository
import kotlinx.coroutines.launch

class TellAboutViewModel(app: Application) : AndroidViewModel(app) {

    private val updateController = UserController(app)
    private val saveAvatar=AvatarController(app)
    private val controllerTopic = TopicController(app)

    private val _isErrorFromTopic = MutableLiveData<Boolean>()
    val isErrorFromTopic: LiveData<Boolean>
        get() = _isErrorFromTopic
    private val _isTopic = MutableLiveData<List<TopicResponse>?>()
    val isTopic: LiveData<List<TopicResponse>?>
        get() = _isTopic

    private val _isErrorAvatar = MutableLiveData<Boolean>()
    val isErrorAvatar: LiveData<Boolean>
        get() = _isErrorAvatar

    private val topicRepository = TopicRepository(app)
    val topics = topicRepository.observeAllProfiles()


    private val _isErrorUpdateProfile = MutableLiveData<Boolean>()
    val isErrorUpdateProfile: LiveData<Boolean>
        get() = _isErrorUpdateProfile

    private val _isErrorSaveAvatar = MutableLiveData<Boolean>()
    val isErrorSaveAvatar: LiveData<Boolean>
        get() = _isErrorSaveAvatar



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
                _isErrorFromTopic.value=false
                add(it)

            },
            onFailure = {
                _isErrorFromTopic.value=true
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