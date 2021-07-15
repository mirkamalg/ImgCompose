package com.mirkamalg.imgcompose.network.exceptions

/**
 * Created by Mirkamal on 14 İyun 2021
 */
class UnknownCodeException(code: Int?) : BaseNetworkException("Unknown code: $code")