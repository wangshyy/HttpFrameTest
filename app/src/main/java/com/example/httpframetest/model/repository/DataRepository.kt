package com.example.httpframetest.model.repository

import com.example.httpframetest.core.Result
import com.example.httpframetest.model.api.BaseRepository
import com.example.httpframetest.model.api.ESMSService
import com.example.httpframetest.model.bean.DataResponse

/**
 *  author : wsy
 *  date   : 2023/2/23
 *  desc   : repository
 */
class DataRepository(private val service: ESMSService) : BaseRepository() {
    suspend fun getData(): Result<DataResponse> {
        return safeApiCall(
            call = { executeResponse(service.getData()) },
            errorMessage = "网络错误"
        )
    }
}