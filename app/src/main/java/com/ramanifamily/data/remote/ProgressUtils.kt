package com.ramanifamily.data.remote

import com.ramanifamily.data.remote.ApiState

object ProgressUtils {
    fun show() = ApiState.Loading
    fun hide() = ApiState.Idle
}
