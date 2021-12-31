package com.emad.payroll.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Payroll(
    val Activation: Boolean,
    val ArabicMessage: String,
    val EnglishMessage: String,
    val Payroll: PayrollX,
    val Success: Boolean
): Parcelable {
    @Keep
    @Parcelize
    data class Allowence(
        val COMP_DESC_AR: String,
        val COMP_DESC_EN: String,
        val EMP_ID: Int,
        val SAL_COMP_CODE: Int,
        val SAL_COMP_TYPE: Int,
        val SAL_VALUE: Double
    ): Parcelable
    @Keep
    @Parcelize
    data class Deduction(
        val COMP_DESC_AR: String,
        val COMP_DESC_EN: String,
        val EMP_ID: Int,
        val SAL_COMP_CODE: Int,
        val SAL_COMP_TYPE: Int,
        val SAL_VALUE: Double
    ): Parcelable
    @Keep
    @Parcelize
    data class Employee(
        val ATM_ACCOUNT: String,
        val COMP_DESC_A_AR: String,
        val COMP_DESC_A_EN: String,
        val COMP_DESC_D_AR: String,
        val COMP_DESC_D_EN: String,
        val CONTRACTSTDATE: String,
        val CURRSYMBOL_AR: String,
        val CURRSYMBOL_EN: String,
        val CUSTOM_ID: Double,
        val EMP_DATA_AR: String,
        val EMP_DATA_EN: String,
        val EMP_GENDUR: String,
        val EMP_ID: Int,
        val EMP_PIC: String,
        val FRACTIONNAME_AR: String,
        val FRACTIONNAME_EN: String,
        val JOBCODE: Double,
        val JOBNAME_AR: String,
        val JOBNAME_EN: String,
        val MAR_STATUS_AR: String,
        val MAR_STATUS_EN: String,
        val SAL_COMP_CODE_A: Double,
        val SAL_COMP_CODE_D: Double,
        val SAL_VALUE_A: Double,
        val SAL_VALUE_D: Double,
        val SAL_VALUE_NET: Double,
        val SEC_NAME_AR: String,
        val SEC_NAME_EN: String,
        val STATUSNAME_AR: String,
        val STATUSNAME_EN: String,
        val STATUS_AR: String,
        val STATUS_EN: String
    ): Parcelable
    @Keep
    @Parcelize
    data class PayrollX(
        val Date: String,
        val Allowences: List<Allowence>,
        val Deduction: List<Deduction>,
        val Employee: List<Employee>
    ): Parcelable
}