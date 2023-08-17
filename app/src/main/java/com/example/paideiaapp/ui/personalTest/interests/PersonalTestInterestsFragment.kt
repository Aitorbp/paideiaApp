package com.example.paideiaapp.ui.personalTest.interests

import android.animation.ObjectAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.example.paideiaapp.commons.Constants.interestsList
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.paideiaapp.R
import com.example.paideiaapp.databinding.FragmentPersonalTestInterestsBinding
import com.example.paideiaapp.ui.main.MainState
import com.example.paideiaapp.ui.main.buildMainState
import com.example.paideiaapp.ui.personalTest.PersonalTestListener
import com.example.paideiaapp.ui.personalTest.PersonalTestViewModel
import com.google.android.material.chip.Chip


class PersonalTestInterestsFragment : Fragment(), PersonalTestListener {

    lateinit var binding: FragmentPersonalTestInterestsBinding

    private val viewModel: PersonalTestViewModel by viewModels({ requireParentFragment() })

    private lateinit var mainState: MainState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_personal_test_interests,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        renderListInterests()

        handleProgressStepperIndicator()
        enableContinueBotton()
        continueButton()
        binding.personalTestInterestsToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

    }

    private fun handleProgressStepperIndicator() {
        // Obtiene el progreso actual del ProgressBar
        val currentProgress = viewModel.progressStepperIndicator
        var newProgress = 2
        val animator = ObjectAnimator.ofInt(
            binding.progressIndicator,
            "progress",
            currentProgress,
            newProgress
        )
        animator.duration = 300 // Duración de la animación en milisegundos
        animator.interpolator = DecelerateInterpolator()
        animator.start()
        viewModel.progressStepperIndicator = newProgress
    }

    private fun renderListInterests() {
        for (interest in interestsList) {
            val chip = Chip(binding.chipGroup.context)
            chip.text = interest
            chip.isClickable = true
            chip.isCheckable = true
            chip.id = View.generateViewId()
            if (viewModel.selectedInterest.contains(chip.text)) { //Comprobamos si ya hay chips chequeados para matenerlos
                chip.isChecked = true
            }
            handleSelectedInterests(chip)

            binding.chipGroup.addView(chip)
        }
    }

    private fun handleSelectedInterests(chip: Chip) {
        chip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.selectedInterest.add(chip.text as String)
                if (viewModel.selectedInterest.size > 4) {
                    // Desactivar el chip si se excede el límite de selección
                    chip.isChecked = false
                    viewModel.selectedInterest.remove(chip.text)
                }
            } else {
                viewModel.selectedInterest.remove(chip.text)
            }
          enableContinueBotton()
        }
    }

    override fun enableContinueBotton() {
       val isSelectedItem =  viewModel.selectedInterest.isNotEmpty()
        if (isSelectedItem) {
            binding.btnContinue.background = ColorDrawable(R.color.color_base_app)
            binding.btnContinue.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.color_base_app)
            binding.btnContinue.setBackgroundResource(R.drawable.button_on)
        } else {
            binding.btnContinue.background = ColorDrawable(R.color.gray)
            binding.btnContinue.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.gray)
            binding.btnContinue.setBackgroundResource(R.drawable.button_off)
        }
    }

    override fun continueButton() {
        binding.btnContinue.setOnClickListener {
            for (interest in interestsList) {
                Log.d("sdsds", viewModel.selectedInterest.toString())
            }
            if (viewModel.selectedInterest.isNotEmpty()) {
                mainState.navigateTo(R.id.action_personalTestInterestsFragment_to_personalTestPersonalityTrails)
            }
        }
    }

}