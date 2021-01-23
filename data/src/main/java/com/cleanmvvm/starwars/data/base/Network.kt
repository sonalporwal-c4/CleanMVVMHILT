package com.cleanmvvm.starwars.data.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Response
import java.net.HttpURLConnection

class FailedRequest(val responseCode: Int, cause: Throwable? = null) : RuntimeException("Failed network call", cause)

abstract class BaseException(message: String, val errorCode: Int, cause: Throwable? = null) : RuntimeException(message) {
    init {
        cause?.let { initCause(it) }
    }
}

class UnExpectedException(cause: Throwable? = null) : BaseException(message = "UnExpected Error", errorCode = -1, cause = cause)

/** Use this function to add retry capabilities to your retrofit calls */
@ExperimentalCoroutinesApi
suspend fun <ResponseType> retry(count: Int = 1, requestCall: suspend () -> Response<ResponseType>): Response<ResponseType> {
    try {
        val response = requestCall()
        return when (response.code()) {
            in 500..599 -> {
                if (count > 0) {
                    retry(count - 1, requestCall)
                } else {
                    response
                }
            }
            else -> response
        }
    } catch (e: Exception) {
        if (count > 0) {
            return retry(count - 1, requestCall)
        } else {
            throw FailedRequest(-1, e)
        }
    }
}

/** This function will extract the response value or throw an exception */
@ExperimentalCoroutinesApi
suspend fun <ResponseType> returnOrThrow(requestCall: suspend () -> Response<ResponseType>): ResponseType {
    val response = requestCall()
    return when (response.code()) {
        HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_CREATED -> response.body()!!
        else -> throw FailedRequest(response.code())
    }
}


