package com.ramanifamily.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import com.ramanifamily.R

@Composable
fun LoadingOverlay(isLoading: Boolean) {
    if (!isLoading) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            // 🔒 Silently consumes ALL touch events
            .pointerInput(Unit) {
                // empty → just consume
            }
            .background(Color.Black.copy(alpha = 0.35f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorResource(R.color.green_700)
        )
    }
}
