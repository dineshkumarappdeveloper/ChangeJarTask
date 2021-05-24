package com.ahzimat.changejar.repository

import com.ahzimat.changejar.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getImages(pageNumber: String) =  apiHelper.getImages(pageNumber)

}