package com.ahzimat.changejar.api

import com.ahzimat.changejar.model.ImagesDataItem
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getImages(pageNumber: String): Response<List<ImagesDataItem>> = apiService.getImages(pageNumber)

}