package com.ramanifamily.presentation.auth

import android.app.Activity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ramanifamily.R
import com.ramanifamily.presentation.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onNavigateLogin: () -> Unit,
    onNavigateDashboard: () -> Unit
) {

    val view = LocalView.current
    val window = (view.context as Activity).window

    // 🔹 Hide system bars only for splash
    DisposableEffect(Unit) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, view)
        controller.hide(
            WindowInsetsCompat.Type.statusBars() or
                    WindowInsetsCompat.Type.navigationBars()
        )
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        onDispose {
            controller.show(
                WindowInsetsCompat.Type.statusBars() or
                        WindowInsetsCompat.Type.navigationBars()
            )
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
    }

    // Observe login state
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(initial = false)

    LaunchedEffect(Unit) {
        delay(2000)
        if (isLoggedIn) onNavigateDashboard() else onNavigateLogin()
    }

    // Animations
    val imageScale = remember { Animatable(0.8f) }
    val imageAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        imageAlpha.animateTo(1f, tween(1200))
        imageScale.animateTo(1f, tween(900, easing = FastOutSlowInEasing))
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(R.drawable.splash_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(R.drawable.ramani_family),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    scaleX = imageScale.value
                    scaleY = imageScale.value
                    alpha = imageAlpha.value
                }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.str_ramani_parivar),
                color = colorResource(R.color.green_700),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.str_aapnu),
                color = colorResource(R.color.green_700),
                fontSize = 18.sp
            )
        }
    }
}


/*import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramanifamily.R
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    val view = LocalView.current
    val window = (view.context as Activity).window

    DisposableEffect(Unit) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, view)
        controller.hide(
            WindowInsetsCompat.Type.statusBars() or
                    WindowInsetsCompat.Type.navigationBars()
        )
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        onDispose {
            controller.show(
                WindowInsetsCompat.Type.statusBars() or
                        WindowInsetsCompat.Type.navigationBars()
            )
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
    }

    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        val imageScale = remember { Animatable(0.8f) }
        val imageAlpha = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            imageAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(1200)
            )

            imageScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(900, easing = FastOutSlowInEasing)
            )
        }

        Image(
            painter = painterResource(R.drawable.ramani_family),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = imageScale.value
                    scaleY = imageScale.value
                    alpha = imageAlpha.value
                }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.str_ramani_parivar),
                color = colorResource(id = R.color.green_700),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(id = R.string.str_aapnu),
                color = colorResource(id = R.color.green_700),
                fontSize = 18.sp
            )
        }
    }
}

// Activity extension functions
fun ComponentActivity.hideSystemBars() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        window.insetsController?.systemBarsBehavior =
            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
}

fun ComponentActivity.showSystemBars() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}


@Preview(name = "Small Device", widthDp = 320, heightDp = 568)
@Preview(name = "Large Device", widthDp = 411, heightDp = 731)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onTimeout = {})
}*/
