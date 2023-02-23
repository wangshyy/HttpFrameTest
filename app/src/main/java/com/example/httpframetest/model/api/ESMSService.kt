package com.example.httpframetest.model.api

import com.example.httpframetest.BuildConfig
import retrofit2.http.GET

/**
 *  author : wsy
 *  date   : 2023/2/15
 *  desc   : 网络请求接口
 */
interface ESMSService {
    companion object{
        const val BASE_URL = BuildConfig.BASE_URL
    }

    @GET("api/pad/config/params")
    suspend fun getData():

}