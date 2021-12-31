package com.emad.payroll.data.model.requests

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PieChartModel(val Title: String, val Value: Float): Parcelable