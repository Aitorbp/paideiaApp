package com.example.paideiaapp.ui.personalTest.BranchKnowledge

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paideiaapp.R
import com.example.paideiaapp.databinding.FragmentPersonalTestBranchknowLedgeBinding
import com.example.paideiaapp.models.appModels.BranchKnowledge
import com.example.paideiaapp.ui.main.MainState
import com.example.paideiaapp.ui.main.buildMainState
import com.example.paideiaapp.ui.personalTest.PersonalTestListener
import com.example.paideiaapp.ui.personalTest.PersonalTestViewModel


class PersonalTestBranchKnowledgeFragment : Fragment(), PersonalTestListener {

    private val viewModel: PersonalTestViewModel by viewModels({ requireParentFragment() })
    private val adapter = PersonalTestBranchKnowledgeAdapter { }
    lateinit var binding: FragmentPersonalTestBranchknowLedgeBinding

    private lateinit var mainState: MainState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_personal_test_branchknow_ledge,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()


        adapter.submitList(getBranchKnowledgeList())
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager as GridLayoutManager

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                enableContinueBotton()
            }
        })

        binding.progressIndicator.progress = viewModel.progressStepperIndicator
        binding.personalTestBranchToolbar.setNavigationOnClickListener {
            viewModel.clean()
            requireActivity().onBackPressed()
        }
        enableContinueBotton()
        continueButton()
        returnToMain()
    }

    private fun returnToMain() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Realizar la lógica deseada al presionar el botón de retroceso
                    // Por ejemplo, mostrar un diálogo de confirmación o navegar hacia atrás en la navegación del Fragment

                    viewModel.clean()
                    findNavController().popBackStack()
                }
            })
    }


    fun getBranchKnowledgeList(): MutableList<BranchKnowledge> {
        val branchKnowledgeList = mutableListOf<BranchKnowledge>()
        branchKnowledgeList.add(
            BranchKnowledge(
                getString(R.string.personal_test_science_sport),
                R.drawable.ic_science_sports
            )
        )
        branchKnowledgeList.add(
            BranchKnowledge(
                getString(R.string.personal_test_arts_and_humanities),
                R.drawable.ic_arts_humanities
            )
        )
        branchKnowledgeList.add(
            BranchKnowledge(
                getString(R.string.personal_test_engineering_and_architecture),
                R.drawable.ic_engineering_architecture
            )
        )
        branchKnowledgeList.add(
            BranchKnowledge(
                getString(R.string.personal_test_social_and_legal_sciences),
                R.drawable.ic_social_and_legal_sciences
            )
        )
        branchKnowledgeList.add(
            BranchKnowledge(
                getString(R.string.personal_test_health_sciences),
                R.drawable.ic_science
            )
        )
        return branchKnowledgeList
    }

    override fun enableContinueBotton() {
        viewModel.selectedBranchknoledge1 = adapter.getSelectedItems()
        val isSelected = viewModel.selectedBranchknoledge1.isNotEmpty()

        if (isSelected) {
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
            Log.d("fff", "tddr")
            if (viewModel.selectedBranchknoledge1.isNotEmpty()) {
                mainState.navigateTo(R.id.action_personalTestFragment_to_personalTestInterestsFragment)
            }
        }
    }
}