package com.example.paideiaapp.server


import com.example.paideiaapp.commons.Constants.API_KEY
import com.example.paideiaapp.commons.Constants.GENERATE_COMPLETIONS
import com.example.paideiaapp.models.serverModels.GptRequest
import com.example.paideiaapp.models.serverModels.GptResponse
import retrofit2.http.*

interface RemoteService {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $API_KEY"
    )
    @POST(GENERATE_COMPLETIONS)
    suspend fun getCompletion( @Body requestBody: GptRequest): GptResponse

}