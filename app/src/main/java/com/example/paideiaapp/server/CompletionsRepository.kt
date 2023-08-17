package com.example.paideiaapp.server

import android.util.Log
import com.example.paideiaapp.models.serverModels.Choice
import com.example.paideiaapp.models.serverModels.GptRequest
import com.example.paideiaapp.server.RemoteConnection.service

class CompletionsRepository {

    suspend fun getCompletion(gptBody: GptRequest): ApiResponseStatus<List<Choice>>
            = makeNetworkCall {
        val completionsResponseModel  = service.getCompletion(gptBody)
        Log.d("GPT1", completionsResponseModel.choices.get(0).text)
        completionsResponseModel.choices
    }
}