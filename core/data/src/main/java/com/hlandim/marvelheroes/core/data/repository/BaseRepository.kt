package com.hlandim.marvelheroes.core.data.repository

import com.hlandim.marvelheroes.core.data.util.DataResponse
import com.hlandim.marvelheroes.network.util.NetworkCheck
import retrofit2.HttpException
import retrofit2.Response

class NoConnectionException : Throwable("No Connection Available!")
open class BaseRepository(private val networkCheck: NetworkCheck) {

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>,
    ): DataResponse<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                DataResponse.Success(body)
            } else {
                DataResponse.Error(
                    code = response.code(),
                    message = response.message(),
                )
            }
        } catch (e: HttpException) {
            DataResponse.Error(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            if (!networkCheck.isOnline()) {
                DataResponse.Exception(NoConnectionException())
            } else {
                DataResponse.Exception(e)
            }
        }
    }
}
