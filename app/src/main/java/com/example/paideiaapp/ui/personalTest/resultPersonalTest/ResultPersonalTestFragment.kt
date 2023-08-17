package com.example.paideiaapp.ui.personalTest.resultPersonalTest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.paideiaapp.R
import com.example.paideiaapp.databinding.FragmentMainBinding
import com.example.paideiaapp.server.ApiResponseStatus
import com.example.paideiaapp.ui.personalTest.PersonalTestViewModel


class ResultPersonalTestFragment : Fragment(R.layout.fragment_result_personal_test) {


    private val viewModel: PersonalTestViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_personal_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMainBinding.bind(view).apply {

            viewModel.completions.observe(viewLifecycleOwner) { choices ->
                Log.d("GPT", choices[0].text)

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