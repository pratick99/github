package com.pratik.github.util

import android.util.Log
import retrofit2.Response

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Log.e(LOG_TAG, message)
        return Result.error("Network call has failed for a following reason: $message")
    }

    companion object {
        private val LOG_TAG = BaseDataSource::class.java.simpleName
    }

}
