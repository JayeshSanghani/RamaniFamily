package com.ramanifamily

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ramanifamily.presentation.AppNavigation
import com.ramanifamily.ui.theme.RamaniFamilyTheme

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set for android 12
        //DO NOT call enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        //Status bar background
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.green_700)

        //White icons
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
        }

        setContent {
            RamaniFamilyTheme() { AppNavigation() }

        }
    }
}
