package com.mirkamalg.repositories

import com.mirkamalg.imgcompose.network.exceptions.*
import retrofit2.Response

/**
 * Created by Mirkamal on 11 İyun 2021
 */
open class BaseRepository {

    /**
     * Handles passed [response] by returning response data [T] if response is successful or throwing
     * a proper [BaseNetworkException]
     * @param [response] response of the sent request
     * @return [T] data of the response if response is successful
     * @throws BaseNetworkException subclass if request is not successful
     *
     * @author Mirkamal Gasimov
     */
    protected fun <T> handleResponse(response: Response<T>?): T {

        if (response?.isSuccessful == true) {
            if (response.body() == null) throw NullBodyException
            return response.body()!!
        } else {

            throw when (response?.code()) {
                401 -> {
                    UnauthorizedException
                }
                404 -> {
                    NotFoundException()
                }
                in 500..599 -> {
                    ServerError
                }
                else -> UnknownCodeException(response?.code())
            }
        }
    }
}