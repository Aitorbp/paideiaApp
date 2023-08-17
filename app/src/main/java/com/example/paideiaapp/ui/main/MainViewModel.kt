package com.example.paideiaapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paideiaapp.models.serverModels.Choice
import com.example.paideiaapp.server.*

class MainViewModel : ViewModel() {

    private val completionsRepository = CompletionsRepository()

    //Principio de encasulamiento. Hacemos un livedata publico para que solo sea editable desde dentro del vm y nada mas.
    //Desde fuera del vm vamos a poder leer la lista, pero no editarla
    private val _completions = MutableLiveData<List<Choice>>()
    val completions: LiveData<List<Choice>>
    get() = _completions

    private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status


}