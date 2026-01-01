package com.ramanifamily.data.remote

import com.google.gson.Gson

object ApiErrorParser {

    private val gson = Gson()

    fun parse(errorBody: String?): String {
        return try {
            if (errorBody.isNullOrEmpty()) {
                DEFAULT_ERROR
            } else {
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)

                when {
                    !errorResponse.message.isNullOrBlank() -> errorResponse.message

                    !errorResponse.errors.isNullOrEmpty() -> errorResponse.errors.values.firstOrNull()
                                                                                    ?.firstOrNull()
                                                                                    ?: DEFAULT_ERROR

                    else -> DEFAULT_ERROR
                }
            }
        } catch (e: Exception) {
            DEFAULT_ERROR
        }
    }

    private const val DEFAULT_ERROR = "Something went wrong"
}
