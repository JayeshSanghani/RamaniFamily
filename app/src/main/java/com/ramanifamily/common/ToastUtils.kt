package com.ramanifamily.common


import android.content.Context
import android.widget.Toast

object ToastUtils {

    fun show(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun show(context: Context, messageRes: Int) {
        Toast.makeText(context, context.getString(messageRes), Toast.LENGTH_SHORT).show()
    }
}
