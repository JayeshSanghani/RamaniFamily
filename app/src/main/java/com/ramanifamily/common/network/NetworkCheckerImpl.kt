package com.ramanifamily.common.network

import android.content.Context

class NetworkCheckerImpl(
    private val context: Context
) : NetworkChecker {

    override fun isConnected(): Boolean {
        return NetworkUtil.isNetworkAvailable(context)
    }
}