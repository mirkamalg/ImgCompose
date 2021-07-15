package com.mirkamalg.imgcompose.network.exceptions

import java.io.IOException

/**
 * Created by Mirkamal on 14 İyun 2021
 */

open class BaseNetworkException(error: String?) : IOException(error)
