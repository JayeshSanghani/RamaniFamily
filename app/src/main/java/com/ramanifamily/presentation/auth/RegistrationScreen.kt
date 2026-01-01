package com.ramanifamily.presentation.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramanifamily.R
import com.ramanifamily.common.AppOutlinedDropdownField
import com.ramanifamily.common.AppOutlinedTextField
import com.ramanifamily.common.CustomButton
import com.ramanifamily.common.ToastUtils
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramanifamily.common.LoadingOverlay
import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.remote.ApiState
import com.ramanifamily.data.remote.AppModule
import com.ramanifamily.presentation.viewmodel.RegisterViewModel
import com.ramanifamily.presentation.viewmodel.RegisterViewModelFactory
import com.ramanifamily.ui.theme.RamaniFamilyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen( navController: NavController) {

//    val view = LocalView.current
//    val context = view.context
//    val window = (view.context as Activity).window
//
//    DisposableEffect(Unit) {
//        window.statusBarColor = ContextCompat.getColor(view.context, R.color.green_700)
//        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = false
//        onDispose { }
//    }

    val view = LocalView.current
    val context = view.context

    var fName by remember { mutableStateOf("") }
    var mName by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var taluka by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var maritalStatus by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("") }
    var mobileNo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val districtList = remember {
        listOf("District 1", "District 2", "District 3")
    }

    val talukaList = remember {
        listOf("Taluka 1", "Taluka 2", "Taluka 3")
    }

    val villageList = remember {
        listOf("Village 1", "Village 2", "Village 3")
    }

    val maritalStatusList = remember {
        listOf("Married", "Unmarried", "Divorced")
    }

    val bloodGroupList = remember {
        listOf("A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-")
    }

    val viewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory(AppModule.registerUserUseCase, AppModule.networkChecker))
    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(registerState) {
        when (registerState) {
            is ApiState.Success -> {
                ToastUtils.show(context, (registerState as ApiState.Success).data.message)
                viewModel.resetState()
                navController.navigate("login") {
                    popUpTo("registration") { inclusive = true }
                }
            }

            is ApiState.Error -> {
                ToastUtils.show(context, (registerState as ApiState.Error).message)
                viewModel.resetState()
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            ToolBarRegister(navController)
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .background(colorResource(R.color.white))
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .padding(16.dp, 0.dp, 16.dp, 8.dp)
            ) {
                CustomButton(
                    text = stringResource(R.string.str_submit),
                    enabled = registerState !is ApiState.Loading,
                    onClick = {
                        when {
                            fName.isBlank() -> ToastUtils.show(context, R.string.ent_firstname)
                            mName.isBlank() -> ToastUtils.show(context, R.string.ent_middlename)
                            surname.isBlank() -> ToastUtils.show(context, R.string.ent_surname)
                            district.isBlank() -> ToastUtils.show(context, R.string.sel_district)
                            taluka.isBlank() -> ToastUtils.show(context, R.string.sel_taluka)
                            village.isBlank() -> ToastUtils.show(context, R.string.sel_village)
                            maritalStatus.isBlank() -> ToastUtils.show(context, R.string.str_sel_marital_status)
                            bloodGroup.isBlank() -> ToastUtils.show(context, R.string.str_sel_blood_group)
                            mobileNo.isBlank() -> ToastUtils.show(context, R.string.ent_mobile_no)
                            mobileNo.length != 10 -> ToastUtils.show(context, R.string.ent_mobile)
                            password.isBlank() -> ToastUtils.show(context, R.string.ent_password)
                            else -> {
                                val TAG = "Reg"
                                Log.e(TAG, fName)
                                Log.e(TAG, mName)
                                Log.e(TAG, surname)
                                Log.e(TAG, district)
                                Log.e(TAG, taluka)
                                Log.e(TAG, village)
                                Log.e(TAG, maritalStatus)
                                Log.e(TAG, bloodGroup)
                                Log.e(TAG, mobileNo)
                                Log.e(TAG, password)

                                val request = RegisterRequest(
                                    firstName = fName,
                                    middleName = mName,
                                    surname = surname,
                                    district = district,
                                    taluka = taluka,
                                    village = village,
                                    maritalStatus = maritalStatus,
                                    bloodGroup = bloodGroup,
                                    mobile = mobileNo,
                                    password = password
                                )

                                viewModel.registerUser(request)
                            }
                        }
                    },
                    backgroundColor = R.color.green_700,
                    textColor = R.color.white,
                    cornerRadius = 8.dp
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.white))
                .padding(16.dp, 8.dp, 16.dp, 0.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {

            AppOutlinedTextField(
                value = fName,
                onValueChange = { fName = it },
                label = stringResource(R.string.str_firstname)
            )

            AppOutlinedTextField(
                value = mName,
                onValueChange = { mName = it },
                label = stringResource(R.string.str_middlename)
            )

            AppOutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                label = stringResource(R.string.str_surname)
            )

            AppOutlinedDropdownField(
                value = district,
                label = stringResource(R.string.sel_district),
                options = districtList,
                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                onValueChange = { district = it }
            )

            AppOutlinedDropdownField(
                value = taluka,
                label = stringResource(R.string.sel_taluka),
                options = talukaList,
                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                onValueChange = { taluka = it }
            )

            AppOutlinedDropdownField(
                value = village,
                label = stringResource(R.string.sel_village),
                options = villageList,
                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                onValueChange = { village = it }
            )

            AppOutlinedDropdownField(
                value = maritalStatus,
                label = stringResource(R.string.str_sel_marital_status),
                options = maritalStatusList,
                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                onValueChange = { maritalStatus = it }
            )

            AppOutlinedDropdownField(
                value = bloodGroup,
                label = stringResource(R.string.str_sel_blood_group),
                options = bloodGroupList,
                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                onValueChange = { bloodGroup = it }
            )

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
        }
    }

    LoadingOverlay(
        isLoading = registerState is ApiState.Loading
    )
}

@Composable
fun ToolBarRegister(navController : NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.green_700))
            .statusBarsPadding()
            .height(56.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .clickable {
                    navController.popBackStack()
                }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = stringResource(R.string.str_register).uppercase(),
            color = Color.White,
            fontSize = dimensionResource(id = R.dimen.font_size_title).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(36.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreenPreview() {
    RamaniFamilyTheme {
        RegistrationScreen(
            navController = rememberNavController()
        )
    }
}
