package com.mirkamalg.repositories

import com.mirkamalg.imgcompose.data.PhotosData
import com.mirkamalg.imgcompose.network.NetworkInitializer

class PhotosRepository: BaseRepository() {

    private val photoServices = NetworkInitializer.photoServices

    suspend fun fetchPhotos(): List<PhotosData> {
        return handleResponse(photoServices.fetchPhotos())
    }

}