package com.example.paideiaapp.ui.personalTest.personalityTrails

import android.animation.ObjectAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.paideiaapp.R
import com.example.paideiaapp.commons.Constants.agreeableness
import com.example.paideiaapp.commons.Constants.extraversion
import com.example.paideiaapp.databinding.FragmentPersonalTestPersonalityTrailsBinding
import com.example.paideiaapp.ui.main.MainState
import com.example.paideiaapp.ui.main.buildMainState
import com.example.paideiaapp.ui.personalTest.PersonalTestListener
import com.example.paideiaapp.ui.personalTest.PersonalTestViewModel
import com.google.android.material.chip.Chip


class PersonalTestPersonalityTrails : Fragment(), PersonalTestListener {

    lateinit var binding: FragmentPersonalTestPersonalityTrailsBinding

    private val viewModel: PersonalTestViewModel by viewModels({ requireParentFragment() })

    private lateinit var mainState: MainState



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_personal_test_personality_trails,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()

        binding.personalTestPersonalityTrailToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        handleProgressStepperIndicator()
        renderListPersonalityTrails()
        enableContinueBotton()
        continueButton()
    }

    private fun handleProgressStepperIndicator() {
        // Obtiene el progreso actual del ProgressBar
        val currentProgress = viewModel.progressStepperIndicator
        var newProgress = 40
        val animator = ObjectAnimator.ofInt(binding.progressIndicator, "progress", currentProgress, newProgress)
        animator.duration = 300 // Duración de la animación en milisegundos
        animator.interpolator = DecelerateInterpolator()
        animator.start()
        viewModel.progressStepperIndicator = newProgress
    }
    private fun renderListPersonalityTrails() {
        for (interest in extraversion) {
            val chip = Chip(binding.chipGroupExtraversion.context)
            chip.text = interest
            chip.isClickable = true
            chip.isCheckable = true
            handleSelectedPersonalityTrails(chip)
            binding.chipGroupExtraversion.addView(chip)
        }

        for (interest in agreeableness) {
            val chip = Chip(binding.chipGroupAgreeableness.context)
            chip.text = interest
            chip.isClickable = true
            chip.isCheckable = true
            handleSelectedPersonalityTrails(chip)
            binding.chipGroupAgreeableness.addView(chip)
        }
    }

    private fun handleSelectedPersonalityTrails(chip: Chip) {
        chip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.selectedPersonalityTrials.add(chip.text as String)
                if (viewModel.selectedPersonalityTrials.size > 2) {
                    // Desactivar el chip si se excede el límite de selección
                    chip.isChecked = false
                    viewModel.selectedPersonalityTrials.remove(chip.text)
                }
            } else {
                viewModel.selectedPersonalityTrials.remove(chip.text)
            }
            enableContinueBotton()
        }

    }

    override fun enableContinueBotton() {
       val isSelectedItem =  viewModel.selectedPersonalityTrials.isNotEmpty()
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
            if(viewModel.selectedPersonalityTrials.isNotEmpty()){
                mainState.navigateTo(R.id.action_personalTestPersonalityTrails_to_typeWorkFragment)
            }
        }


    }


}