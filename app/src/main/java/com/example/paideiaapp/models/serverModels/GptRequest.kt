package com.example.paideiaapp.models.serverModels

data class GptRequest(
    val prompt: String,
    val max_tokens: Int, //he maximum number of tokens to generate in the completion.
    val model: String,
    val temperature: Double, //What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.
    val n: Int //How many completions to generate for each prompt.
)