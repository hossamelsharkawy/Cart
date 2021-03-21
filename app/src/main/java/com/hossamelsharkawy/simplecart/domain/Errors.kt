package com.hossamelsharkawy.simplecart.domain

class FailedToRequestException(message: String? = null) : RuntimeException(message)

class ValidationException(message: String) : java.lang.RuntimeException(message)