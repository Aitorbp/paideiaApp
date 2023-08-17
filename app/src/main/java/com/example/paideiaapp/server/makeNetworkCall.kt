package com.example.paideiaapp.server

import android.util.Log
import com.example.paideiaapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(call: suspend  () -> T): ApiResponseStatus<T> =
    withContext(Dispatchers.IO){
        try {
            ApiResponseStatus.Success(call())
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknown_host_exception_error)
        } catch (e: Exception) {
            Log.d("Aitor", e.message.toString())
            ApiResponseStatus.Error(R.string.unknown_error)
        }
    }