package com.brunofelixdev.kotlincovidstate.handler

import java.io.IOException
import java.lang.RuntimeException

class ApiException(message: String) : RuntimeException(message)
class NoInternetException(message: String) : IOException(message)
