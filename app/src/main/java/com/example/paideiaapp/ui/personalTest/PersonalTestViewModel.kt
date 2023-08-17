package com.example.paideiaapp.ui.personalTest

import android.app.Application
import androidx.lifecycle.*
import com.example.paideiaapp.R
import com.example.paideiaapp.models.appModels.BranchKnowledge
import com.example.paideiaapp.server.ApiResponseStatus
import com.example.paideiaapp.models.serverModels.Choice
import com.example.paideiaapp.server.CompletionsRepository
import com.example.paideiaapp.models.serverModels.GptRequest
import kotlinx.coroutines.launch

class PersonalTestViewModel: ViewModel(){

  //  val context = getApplication<Application>().applicationContext
    private val completionsRepository = CompletionsRepository()

    //Principio de encasulamiento. Hacemos un livedata publico para que solo sea editable desde dentro del vm y nada mas.
    //Desde fuera del vm vamos a poder leer la lista, pero no editarla
    private val _completions = MutableLiveData<List<Choice>>()
    val completions: LiveData<List<Choice>>
        get() = _completions

    private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status

    var selectedBranchknoledge1: List<BranchKnowledge> = emptyList()

    private val _selectedBranchknoledge = MutableLiveData<List<BranchKnowledge>>()
    val selectedBranchknoledge: LiveData<List<BranchKnowledge>>
        get() = _selectedBranchknoledge

    var selectedInterest = mutableSetOf<String>()
    var selectedPersonalityTrials = mutableSetOf<String>()
    var progressStepperIndicator = 0


     fun clean(){
         selectedBranchknoledge1 = emptyList()
         selectedInterest = mutableSetOf<String>()
         selectedPersonalityTrials = mutableSetOf<String>()
         progressStepperIndicator = 0
     }
    init {
        val request = GptRequest(
            prompt = "Recomiendame 5 universidades para estudiar y ponmelo en formato json pero en una linea todo",
            max_tokens = 100,
            temperature = 0.8,
            model = "text-davinci-003",
            n = 1
        )

        downloadCompletions(request)
    }

    private fun downloadCompletions(request: GptRequest) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            // handleResponseStatus(completionsRepository.getCompletion(request)) descomentar esto para hacer la llamada
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Choice>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _completions.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus as ApiResponseStatus<Any>


    }
}