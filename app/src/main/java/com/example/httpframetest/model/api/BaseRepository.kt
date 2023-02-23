package com.example.httpframetest.model.api

import android.util.Log
import com.example.httpframetest.model.bean.ESMSResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import com.example.httpframetest.core.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

/**
 * Created by Jeff
 * on 2020/10/31
 */
open class BaseRepository {

    val JSON = "application/json".toMediaTypeOrNull()

    suspend fun <T : Any> apiCall(call: suspend () -> ESMSResponse<T>): ESMSResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            Log.e("TAG", "safeApiCall: "+e.message )
            // An exception was thrown when calling the API so we're converting this to an IOException
            Result.Error(IOException(errorMessage, e))
        }
    }

    suspend fun <T : Any> executeResponse(response: ESMSResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {
        return coroutineScope {
            if (response.code == 0) {
                successBlock?.let { it() }
                Result.Success(response.data)
            } else {
                errorBlock?.let { it() }
                Result.Error(IOException(response.msg))
            }
        }
    }
}