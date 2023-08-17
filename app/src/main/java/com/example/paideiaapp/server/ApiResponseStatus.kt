package com.example.paideiaapp.server

sealed class ApiResponseStatus<T> {
    class Loading<T>() : ApiResponseStatus<T>()
    class Error<T>(val menssageId: Int): ApiResponseStatus<T>()
    class Success<T>(val data: T): ApiResponseStatus<T>()
}