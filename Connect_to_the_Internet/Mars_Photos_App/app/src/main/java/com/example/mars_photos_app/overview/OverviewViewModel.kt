package com.example.mars_photos_app.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mars_photos_app.network.MarsApi
import com.example.mars_photos_app.network.MarsPhoto
import kotlinx.coroutines.launch
import java.lang.Exception


enum class MarsApiStatus{
    LOADING, SUCCESS, FAILURE
}

class OverviewViewModel : ViewModel() {

//    private val _status = MutableLiveData<String>()
//    val status: LiveData<String> = _status

    private val _marsApiStatus = MutableLiveData<MarsApiStatus>()
    val marsApiStatus: LiveData<MarsApiStatus> = _marsApiStatus

//    private val _photo = MutableLiveData<MarsPhoto>()
//    val photo:LiveData<MarsPhoto> = _photo

    private val _listPhotos = MutableLiveData<List<MarsPhoto>>()
    val listPhotos: LiveData<List<MarsPhoto>> = _listPhotos

    /**
     * Call getMarsPhotos() on init so we can display the [marsApiStatus] immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and set the [listPhotos]
     */
    private fun getMarsPhotos() {
//        _status.value = "Set the Mars API status response here!"
        /**
         * A ViewModelScope is the built-in coroutine scope defined for each ViewModel in your app.
         * Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared.
         */
        viewModelScope.launch {
            _marsApiStatus.value = MarsApiStatus.LOADING
            try {

//            val list:List<MarsPhoto> = MarsApi.retrofitService.getPhotos()
//            _status.value = "Web service sent back ${list.size} Mars photos"

//              _photo.value = MarsApi.retrofitService.getPhotos()[0]
//              _status.value = "Photo url : ${_photo.value?.imgUrl}"

                _listPhotos.value = MarsApi.retrofitService.getPhotos()
//              _status.value = "List of Mars photos retrieved"

                _marsApiStatus.value = MarsApiStatus.SUCCESS

            } catch (e: Exception) {
//              _status.value = "Failure : ${e.message}"

                _marsApiStatus.value = MarsApiStatus.FAILURE
                // set recyclerView data as empty list if failure occurs
                _listPhotos.value = listOf()
            }
        }
    }
}