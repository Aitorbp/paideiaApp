package com.example.paideiaapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.example.paideiaapp.R
import com.example.paideiaapp.databinding.FragmentMainBinding
import com.example.paideiaapp.server.ApiResponseStatus


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainState: MainState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Verificar si el modo noche está habilitado
        val nightMode = AppCompatDelegate.getDefaultNightMode()

// Desactivar el modo noche
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {

            buttonStartTest.setOnClickListener {
                mainState.navigateTo(R.id.action_main_dest_to_personalTestFragment)
            }
            viewModel.completions.observe(viewLifecycleOwner) { choices ->
                for (number in choices) {
                    Log.d("GPT", number.text)
                }
            }
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                is ApiResponseStatus.Error -> {
                    binding.loadingProgressbar.visibility = View.GONE    // Ocultar progressbar
                    Toast.makeText(context, status.menssageId, Toast.LENGTH_SHORT)
                        .show()//Mostrar toast
                }
                is ApiResponseStatus.Loading -> binding.loadingProgressbar.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.loadingProgressbar.visibility =
                    View.GONE // Ocultar progressbar
            }
        }
    }

}