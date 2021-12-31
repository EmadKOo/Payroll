package com.emad.payroll.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Employer(
    val AccountId: Int,
    val AccountRole: String,
    val Activation: Boolean,
    val ArabicMessage: String,
    val Code: Int,
    val CurrentPage: Int,
    val EnglishMessage: String,
    val IsArabic: Boolean,
    val PageCount: Int,
    val Success: Boolean,
    val Token: String,
    val UserRole: Int,
    val UserType: Int,
    val VisitStatus: Int,
    val user: User
): Parcelable {
    @Keep
    @Parcelize
    data class User(
        val AmbulanceProfileId: Int,
        val AspNetUsersId: Int,
        val BirthDate: String,
        val ClassArabicName: String,
        val ClassEnglishName: String,
        val ClassId: Int,
        val Email: String,
        val EmailConfirmed: Boolean,
        val FirstNameAr: String,
        val FirstNameEn: String,
        val Gender: Int,
        val HasInsurance: Boolean,
        val Id: Int,
        val LastNameAr: String,
        val LastNameEn: String,
        val LicenseNumber: Int,
        val MobileNumber: String,
        val PatientImage: String,
        val PhoneNumberConfirmed: Boolean,
        val Source: String
    ): Parcelable
}