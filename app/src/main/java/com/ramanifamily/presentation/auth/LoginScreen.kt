package com.ramanifamily.presentation.auth


import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramanifamily.R
import com.ramanifamily.common.AppOutlinedTextField
import com.ramanifamily.common.CustomButton
import com.ramanifamily.common.LoadingOverlay
import com.ramanifamily.common.ToastUtils
import com.ramanifamily.common.Utils
import com.ramanifamily.data.entity.LoginRequest
import com.ramanifamily.data.remote.AppModule
import com.ramanifamily.presentation.viewmodel.LoginViewModel
import com.ramanifamily.presentation.viewmodel.LoginViewModelFactory
import com.ramanifamily.data.remote.ApiState

@Composable
fun LoginScreen(
    navController: NavController
) {
//    HideStatusBarOnScreen()

    val view = LocalView.current
    val context = view.context

    var mobileNo by remember { mutableStateOf("7600400399") }
    var password by remember { mutableStateOf("123456") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val viewModel : LoginViewModel = viewModel(factory = LoginViewModelFactory(AppModule.loginUserUseCase, AppModule.networkChecker, AppModule.userDataStoreRepository))
    val loginState by viewModel.loginState.collectAsState()
    LaunchedEffect(loginState) {
        when(loginState){
            is ApiState.Success -> {
                viewModel.resetState()
                navController.navigate("dashboard") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is ApiState.Error ->{
                ToastUtils.show(context, (loginState as ApiState.Error).message)
                viewModel.resetState()
            }
            else -> Unit
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ramani_family),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        AppOutlinedTextField(
            value = mobileNo,
            onValueChange = {
                if (it.length <= 10 && it.all { c -> c.isDigit() }) {
                    mobileNo = it
                }
            },
            label = stringResource(R.string.str_mobile_no),
            placeholder = stringResource(R.string.str_mobile_no),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        AppOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(R.string.str_password),
            placeholder = stringResource(R.string.str_password),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation =
                if (isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon =
                if (isPasswordVisible) Icons.Default.VisibilityOff
                else Icons.Default.Visibility,
            onTrailingIconClick = {
                isPasswordVisible = !isPasswordVisible
            }
        )

        CustomButton(
            text = stringResource(R.string.str_submit),
            backgroundColor = R.color.green_700,
            textColor = R.color.white,
            cornerRadius = 8.dp,
            onClick = {
                when {
                    mobileNo.length != 10 -> ToastUtils.show(context, R.string.ent_mobile)
                    password.isBlank() -> ToastUtils.show(context, R.string.ent_password)
//                    !Utils.isValidPassword(password) ->  ToastUtils.show(context, R.string.validate_password)
                    else -> {

                        Log.e("Login", "$mobileNo - $password")
                        val request = LoginRequest(
                            mobile = mobileNo,
                            password = password
                        )
                        viewModel.loginUser(request)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(
                text = stringResource(R.string.str_dont),
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(R.string.str_signup),
                color = colorResource(id = R.color.yellow),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("registration")
                }
            )
        }
    }

    LoadingOverlay(
        isLoading = loginState is ApiState.Loading
    )
}

@Composable
fun HideStatusBarOnScreen() {

    val view = LocalView.current
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current

    if (isPreview) return

    DisposableEffect(Unit) {
        val window = (view.context as Activity).window
        val controller = WindowInsetsControllerCompat(window, view)

        //  Hide ONLY for this screen
        controller.hide(WindowInsetsCompat.Type.statusBars())

        //  Allow swipe only while this screen is visible
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        onDispose {
            //  RESTORE for next screens
            controller.show(WindowInsetsCompat.Type.statusBars())

            // Reset behavior
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController()
    )
}