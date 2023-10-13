package com.hlandim.marvelheroes.core.data.util

/**
 * Created by Hugo Santos on 20/09/2023.
 */
sealed class DataResponse<T> {
    data class Success<T>(val data: T?) : DataResponse<T>()
    data class Error<T>(val data: T? = null, val code: Int? = null, val message: String) :
        DataResponse<T>()

    class Loading<T> : DataResponse<T>()
    data class Exception<T>(val e: Throwable) : DataResponse<T>()
}
