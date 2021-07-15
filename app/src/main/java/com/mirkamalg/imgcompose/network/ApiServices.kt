package com.mirkamalg.imgcompose.network

import com.mirkamalg.imgcompose.data.PhotosData
import retrofit2.Response
import retrofit2.http.GET

interface PhotoServices {
    @GET("photos")
    suspend fun fetchPhotos(): Response<List<PhotosData>>
}