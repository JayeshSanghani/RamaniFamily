package com.ramanifamily.common

import android.util.Log
import android.util.Patterns
import com.ramanifamily.data.remote.ApiErrorParser
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {

    const val NO_INTERNET_CONNECTION = "No internet connection"
    const val NETWORK_ERROR = "Network error, please try again"
    const val SOMETHING_WENT_WRONG = "Something went wrong"

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun calculateAge(dob: String): String {

        Log.e("Utils->calculateAge()", dob )
        if (dob.isBlank()) return ""

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val birthDate = try {
            sdf.parse(dob)
        } catch (e: Exception) {
            null
        } ?: return ""

        val dobCal = Calendar.getInstance()
        dobCal.time = birthDate

        val today = Calendar.getInstance()

        var age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR)

        // Adjust if birthday not yet occurred this year
        if (today.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        Log.e("Utils->return", age.coerceAtLeast(0).toString() )
        return age.coerceAtLeast(0).toString()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8 &&
                password.any { it.isDigit() } &&
                password.any { it.isLetter() }
    }

    fun parseError(response: Response<*>): String {
        return ApiErrorParser.parse(response.errorBody()?.string())
    }
}