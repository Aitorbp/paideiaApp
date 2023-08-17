package com.example.paideiaapp.models.appModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 data class BranchKnowledge (
    val branch: String,
    val icon: Int
 ) : Parcelable

