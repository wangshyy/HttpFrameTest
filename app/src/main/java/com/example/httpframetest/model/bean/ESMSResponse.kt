package com.example.httpframetest.model.bean

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import com.example.httpframetest.core.Result
import java.io.IOException

/**
 * Created by luyao
 * on 2018/3/13 14:38
 */
data class ESMSResponse<out T>(val code: Int, val msg: String, val data: T)

suspend fun <T : Any> ESMSResponse<T>.executeResponse(successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                                     errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {
    return coroutineScope {
        if (code == -1) {
            errorBlock?.let { it() }
            Result.Error(IOException(msg))
        } else {
            successBlock?.let { it() }
            Result.Success(data)
        }
    }
}

suspend fun <T : Any> ESMSResponse<T>.doSuccess(successBlock: (suspend CoroutineScope.(T) -> Unit)? = null): ESMSResponse<T> {
    return coroutineScope {
        if (code != -1) successBlock?.invoke(this, this@doSuccess.data)
        this@doSuccess
    }

}

suspend fun <T : Any> ESMSResponse<T>.doError(errorBlock: (suspend CoroutineScope.(String) -> Unit)? = null): ESMSResponse<T> {
    return coroutineScope {
        if (code == -1) errorBlock?.invoke(this, this@doError.msg)
        this@doError
    }
}

