package com.example.paideiaapp.ui.main

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController


fun Fragment.buildMainState(
    navController: NavController = findNavController()
) = MainState(navController)

class MainState(
    private val navController: NavController
) {
    fun navigateTo(screenDest: Int ) {
         navController.navigate(screenDest)
    }
}