package com.emad.payroll.presentaion.extentions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.emad.payroll.R
import com.emad.payroll.data.model.Payroll
import com.emad.payroll.data.model.requests.PieChartModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun Fragment.setUpPiechart(
    piechart: PieChart,
    showLegend: Boolean = false,
    showText: Boolean = false,
) {
    piechart.let {
        it.setUsePercentValues(true)
        it.getDescription().setEnabled(false)
        it.setExtraOffsets(5f, 10f, 5f, 5f)
        it.setDragDecelerationFrictionCoef(0.95f)
        it.setDrawHoleEnabled(false)
        it.setHoleColor(Color.WHITE)
        it.setTransparentCircleAlpha(60)
        it.setHoleRadius(0f)
        it.setDrawCenterText(true)
        it.setRotationAngle(0f)
        it.setRotationEnabled(true)
        it.animateY(1400, Easing.EasingOption.EaseInOutQuad)
        it.setEntryLabelColor(Color.WHITE)
        it.setEntryLabelTextSize(15f)
        it.setDrawEntryLabels(showText)
        it.legend.isEnabled = showLegend
        it.legend.position = Legend.LegendPosition.LEFT_OF_CHART_CENTER
        it.legend.yEntrySpace = 10f
        it.legend.form = Legend.LegendForm.CIRCLE
        it.legend.setDrawInside(false)
        it.legend.textSize = 10f
    }
}

fun Fragment.getPiechartData(
    allowences: List<Payroll.Allowence>,
    deduction: List<Payroll.Deduction>,
): ArrayList<PieChartModel> {
    val list = ArrayList<PieChartModel>()
    var allowenceVal = 0f
    var deductionVal = 0f
    for (item in allowences)
        allowenceVal += item.SAL_VALUE.toFloat()

    for (item in deduction)
        deductionVal += item.SAL_VALUE.toFloat()

    list.add(PieChartModel(getString(R.string.benefits), allowenceVal))
    list.add(PieChartModel(getString(R.string.deductions), deductionVal))
    return list
}

fun Fragment.getTableData(payroll: Payroll): HashMap<String, String> {
    val data = HashMap<String, String>()

    for (allowance in payroll.Payroll.Allowences)
        data[allowance.COMP_DESC_EN] = allowance.SAL_VALUE.toString()

    for (deduction in payroll.Payroll.Deduction)
        data[deduction.COMP_DESC_EN] = deduction.SAL_VALUE.toString()
    return data
}

fun Fragment.isEnglish(): Boolean {
    if (Locale.getDefault().language.equals("en"))
        return true
    else
        return false
}