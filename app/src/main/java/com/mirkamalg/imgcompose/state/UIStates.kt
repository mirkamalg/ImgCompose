package com.mirkamalg.imgcompose.state

import com.mirkamalg.imgcompose.data.PhotosData

data class BaseState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class HomeState(
    val data: List<PhotosData>
)
