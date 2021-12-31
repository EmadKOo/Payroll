package com.emad.payroll.presentaion.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.emad.payroll.R
import com.emad.payroll.data.model.Payroll
import com.emad.payroll.data.model.requests.PieChartModel
import com.emad.payroll.databinding.FragmentPayrollBinding
import com.emad.payroll.presentaion.extentions.getPiechartData
import com.emad.payroll.presentaion.extentions.getTableData
import com.emad.payroll.presentaion.extentions.isEnglish
import com.emad.payroll.presentaion.extentions.setUpPiechart
import com.emad.payroll.presentaion.viewmodels.PayrollViewModel
import com.emad.payroll.utils.ColorHelper
import com.emad.payroll.utils.State
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@AndroidEntryPoint
class PayrollFragment : Fragment() {
    private lateinit var mBinding: FragmentPayrollBinding
    val payrollViewModel: PayrollViewModel by activityViewModels()
    var pieChartList: ArrayList<PieChartModel> = ArrayList()

    @Inject
    lateinit var colorHelper: ColorHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentPayrollBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPayroll()
    }

    private fun handleViews(payroll: Payroll) {
        mBinding.dateTV.setText(payroll.Payroll.Employee[0].CONTRACTSTDATE.substring(0, 10))
        Glide.with(requireContext()).load(payroll.Payroll.Employee[0].EMP_PIC)
            .error(R.drawable.ic_baseline_person_pin_24).into(mBinding.employerImage)
        setUpPiechart(mBinding.payrollPiechart)
        if (isEnglish())
            handleViewsEn(payroll)
        else
            handleViewsAR(payroll)
    }

    private fun handleViewsEn(payroll: Payroll) {
        mBinding.employerNameTV.setText(payroll.Payroll.Employee[0].EMP_DATA_EN)
        mBinding.employerJobTitleTV.setText(payroll.Payroll.Employee[0].JOBNAME_EN)
        mBinding.totalSalaryValueTV.setText(payroll.Payroll.Employee[0].SAL_VALUE_A.toString() + " " + payroll.Payroll.Employee[0].CURRSYMBOL_EN)
        mBinding.benefitsTV.setText(pieChartList[0].Value.toString() + " " + payroll.Payroll.Employee[0].CURRSYMBOL_EN)
        mBinding.deductionsTV.setText(pieChartList[1].Value.toString() + " " + payroll.Payroll.Employee[0].CURRSYMBOL_EN)
        mBinding.totalSalaryTV.setText((pieChartList[0].Value + pieChartList[1].Value).toString() + " " + payroll.Payroll.Employee[0].CURRSYMBOL_EN)
    }

    private fun handleViewsAR(payroll: Payroll) {
        mBinding.employerNameTV.setText(payroll.Payroll.Employee[0].EMP_DATA_AR)
        mBinding.employerJobTitleTV.setText(payroll.Payroll.Employee[0].JOBNAME_AR)
        mBinding.totalSalaryValueTV.setText(payroll.Payroll.Employee[0].SAL_VALUE_A.toString() + " " + payroll.Payroll.Employee[0].CURRSYMBOL_AR)
        mBinding.benefitsTV.setText(pieChartList[0].Value.toString() + " " + payroll.Payroll.Employee[0].CURRSYMBOL_AR)
        mBinding.deductionsTV.setText(pieChartList[1].Value.toString() + " " + payroll.Payroll.Employee[0].CURRSYMBOL_AR)
        mBinding.totalSalaryTV.setText((pieChartList[0].Value + pieChartList[1].Value).toString() + " " + payroll.Payroll.Employee[0].CURRSYMBOL_AR)
    }
    private fun getPayroll() {
        lifecycleScope.launchWhenStarted {
            payrollViewModel.getPayroll()
            payrollViewModel.payrollStateFlow.collectLatest {
                when (it) {
                    is State.Error -> {
                        Log.d(TAG, "getPayroll: ERROR " + it.data)
                    }
                    is State.Loading -> {
                        Log.d(TAG, "getPayroll: Loading")
                    }
                    is State.Success -> {
                        Log.d(TAG, "getPayroll: SUCCESS " + it.data)
                        pieChartList= getPiechartData(it.data!!.Payroll.Allowences, it.data.Payroll.Deduction)
                        handleViews(it.data)
                        setPiechartData(mBinding.payrollPiechart, "", pieChartList)
                        setupPayrollTable(getTableData(it.data))
                    }
                }
            }
        }
    }

    private fun setPiechartData(
        pieChart: PieChart,
        chartTitle: String,
        piechartList: List<PieChartModel>,
    ) {
        val entries: ArrayList<PieEntry> = ArrayList()

        for (item in piechartList)
            entries.add(PieEntry(item.Value, item.Title))

        val dataSet = PieDataSet(entries, chartTitle)
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.colors = colorHelper.getAllColors()
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        pieChart.setData(data)
        pieChart.invalidate()
    }

    private fun setupPayrollTable(data: HashMap<String, String>) {
        data.onEachIndexed { index, entry ->
            val row = TableRow(requireContext()).apply {
                val text1 = TextView(requireContext()).apply {
                    textSize = 20f
                    setPadding(8,8,8,8)
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    text = (index + 1).toString()
                }
                val text2 = TextView(requireContext()).apply {
                    textSize = 20f
                    setPadding(8,8,8,8)
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    text = entry.key
                }
                val text3 = TextView(requireContext()).apply {
                    textSize = 20f
                    setPadding(8,8,8,8)
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    text = entry.value
                }
                addView(text1)
                addView(text2)
                addView(text3)
                when {
                    (index + 1) % 2 == 0 -> setBackgroundColor(resources.getColor(R.color.yellow))

                    (index + 1) % 3 == 0 -> setBackgroundColor(resources.getColor(R.color.green))

                    else -> setBackgroundColor(resources.getColor(R.color.white))
                }
            }
            mBinding.tableView.addView(row)
        }
    }
    companion object {
        private const val TAG = "PayrollFragment"
    }
}