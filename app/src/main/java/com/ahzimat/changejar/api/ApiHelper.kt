package com.ahzimat.changejar.api

import com.ahzimat.changejar.model.ImagesDataItem
import retrofit2.Response

interface ApiHelper {
    suspend fun getImages(pageNumber: String): Response<List<ImagesDataItem>>
}