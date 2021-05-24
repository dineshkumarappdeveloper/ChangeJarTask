package com.ahzimat.changejar.api

import com.ahzimat.changejar.model.ImagesDataItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("photos/?client_id=jkO8-WIlEEVb_IOMnse5zLaLxQN6OV4zPeURvcrCdsk")
    suspend fun getImages(@Query("page") page : String): Response<List<ImagesDataItem>>

}