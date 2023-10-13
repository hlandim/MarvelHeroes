package com.hlandim.marvelheroes.core.data.repository

import com.hlandim.marvelheroes.core.data.util.DataResponse
import com.hlandim.marvelheroes.network.util.NetworkCheck
import kotlinx.coroutines.flow.FlowCollector
import retrofit2.HttpException

class NoConnectionException : Throwable("No Connection Available!")
open class BaseRepository(private val networkCheck: NetworkCheck) {

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T, B> FlowCollector<DataResponse<T>>.handleApiCall(
        apiCallWork: suspend () -> B,
        onSuccess: suspend (B?) -> Unit,
    ) {
        try {
            val response = apiCallWork()
            onSuccess(response)
        } catch (e: HttpException) {
            emit(DataResponse.Error(message = e.message()))
        } catch (e: Throwable) {
            if (!networkCheck.isOnline()) {
                emit(DataResponse.Exception(NoConnectionException()))
            } else {
                emit(DataResponse.Exception(e))
            }
        }
    }
}
