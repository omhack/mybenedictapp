package com.example.mybenedictapp.core.network

sealed class ApiStatus<T>(val data: T? = null) {
    class Success<T>(data: T) : ApiStatus<T>(data)
    class Error<T>(
        val message: String? = null,
        val errorType: ErrorType? = ErrorType.GeneralError,
        data: T? = null,
    ) : ApiStatus<T>(data)
    class Loading<T> : ApiStatus<T>()
}

enum class ErrorType {
    GeneralError,
    NetworkError
}