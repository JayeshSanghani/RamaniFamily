package com.ramanifamily.data.remote

data class ErrorResponse(
    val status: Boolean? = false,
    val message: String? = null,
    val errors: Map<String, List<String>>? = null
)
