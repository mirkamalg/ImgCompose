package com.mirkamalg.imgcompose.viewmodels

import android.app.Application
import com.mirkamalg.imgcompose.state.HomeState
import com.mirkamalg.repositories.PhotosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private val photosRepository = PhotosRepository()

    private val _homeState = MutableStateFlow(HomeState(listOf()))
    val homeState: StateFlow<HomeState> = _homeState

    fun fetchData() {
        makeRequest(
            sentRequest = {
                photosRepository.fetchPhotos()
            },
            onSuccess = {
                _homeState.value = HomeState(it)
            }
        )
    }

}