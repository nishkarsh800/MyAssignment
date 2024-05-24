package com.nick.assignment.utils

sealed class ResponseState<out T> {
    object Loading : ResponseState<Nothing>()
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Failure(val error: String?) : ResponseState<Nothing>()
}