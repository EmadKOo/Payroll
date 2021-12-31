package com.emad.payroll.utils

import android.graphics.Color
import javax.inject.Inject

class ColorHelper @Inject constructor() {
    val colorsList= arrayListOf<Int>()
    init {
        colorsList.add(Color.parseColor("#F0C419"))
        colorsList.add(Color.parseColor("#556080"))
    }
    fun getAllColors(): ArrayList<Int> = colorsList
}