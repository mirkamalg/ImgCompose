package com.mirkamalg.imgcompose.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosData(
    val albumId: Int?,
    val id: Int?,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?
)
