package com.ramanifamily.presentation.profile

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
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ramanifamily.R
import com.ramanifamily.common.AppOutlinedTextField
import com.ramanifamily.common.CustomButton
import com.ramanifamily.common.LoadingOverlay
import com.ramanifamily.common.ToastUtils
import com.ramanifamily.common.Utils
import com.ramanifamily.data.entity.ProfileBusinessRequest
import com.ramanifamily.data.entity.RegisterRequest
import com.ramanifamily.data.remote.ApiState
import com.ramanifamily.data.remote.AppModule
import com.ramanifamily.presentation.viewmodel.ProfileViewModel
import com.ramanifamily.presentation.viewmodel.ProfileViewModelFactory
import com.ramanifamily.presentation.viewmodel.RegisterViewModel
import com.ramanifamily.presentation.viewmodel.RegisterViewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


@Composable
fun BusinessDetailsScreen(
    navController: NavController
) {
    BusinessDetailsScreenContent(
        onBackClick = { navController.navigateUp() }
    )
}
@Composable
fun BusinessDetailsScreenContent(
    onBackClick: () -> Unit
) {
    val view = LocalView.current
    val context = view.context
    var businessName by remember { mutableStateOf("") }
    var businessAddress by remember { mutableStateOf("") }
    var businessContact by remember { mutableStateOf("") }
    var otherDetail by remember { mutableStateOf("") }
    var showNumber by remember { mutableStateOf(true) }


    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(
            AppModule.userDataStoreRepository,
            AppModule.profilePersonalUseCase,
            AppModule.profileBusinessUseCase,
            AppModule.profileMaritalUseCase,
            AppModule.networkChecker
        )
    )
    val profileBusinessState by viewModel.profileBusinessState.collectAsState()
    val isLoading = profileBusinessState is ApiState.Loading

    LaunchedEffect(profileBusinessState) {
        when (profileBusinessState) {
            is ApiState.Success -> {
                ToastUtils.show(context, (profileBusinessState as ApiState.Success).data.message)
                viewModel.resetBusinessState()
                onBackClick()
            }

            is ApiState.Error -> {
                ToastUtils.show(context, (profileBusinessState as ApiState.Error).message)
                viewModel.resetBusinessState()
            }
            else -> Unit
        }
    }

//    val userDetails by viewModel.userDetails.collectAsState()
//    LaunchedEffect(userDetails) {
//        userDetails?.let { user ->
//
//            businessName = user.bu
//        }
//    }


    Scaffold(
        containerColor = Color.White,
        topBar = {
            BusinessDetailsToolbar(onBackClick = onBackClick)
        },
        bottomBar = {
            BusinessDetailsSaveButtonBar(
                onSaveClick = {
                    when {
                        businessName.isBlank() -> ToastUtils.show(context, R.string.str_business_name)
                        businessAddress.isBlank() -> ToastUtils.show(context, R.string.str_business_address)
                        businessContact.isBlank() -> ToastUtils.show(context, R.string.str_business_contact)
                        businessContact.length != 10 -> ToastUtils.show(context, R.string.ent_mobile)

                        else -> {
                            val TAG = "BusinessDetails"

                            Log.e(TAG, "Business Name: $businessName")
                            Log.e(TAG, "Business Address: $businessAddress")
                            Log.e(TAG, "Show Number: $showNumber")
                            Log.e(TAG, "Other Detail: $otherDetail")
                            Log.e(TAG, "Business Contact: $businessContact")

                            val request = ProfileBusinessRequest(
                                businessName = businessName,
                                businessAddress = businessAddress,
                                showNumber = showNumber,
                                otherDetail = otherDetail,
                                businessContact = businessContact
                            )
                            viewModel.businessState(request)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp, 8.dp, 16.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {

                AppOutlinedTextField(
                    value = businessName,
                    onValueChange = { businessName = it },
                    label = stringResource(R.string.str_business_name),
                    placeholder = stringResource(R.string.str_business_name)
                )

                AppOutlinedTextField(
                    value = businessAddress,
                    onValueChange = { businessAddress = it },
                    label = stringResource(R.string.str_business_address),
                    placeholder = stringResource(R.string.str_business_address)
                )

                AppOutlinedTextField(
                    value = businessContact,
                    onValueChange = {
                        if (it.length <= 10 && it.all { c -> c.isDigit() }) {
                            businessContact = it
                        }
                    },
                    label = stringResource(R.string.str_business_contact),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = stringResource(R.string.str_business_contact)
                )

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(R.string.str_show_number),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = showNumber,
                        onClick = { showNumber = true },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.green_700),
                            unselectedColor = Color.Gray
                        )
                    )
                    Text(text = "Yes")

                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = !showNumber,
                        onClick = { showNumber = false },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.green_700),
                            unselectedColor = Color.Gray
                        )
                    )
                    Text(text = "No")
                }

                AppOutlinedTextField(
                    value = otherDetail,
                    onValueChange = { otherDetail = it },
                    label = stringResource(R.string.str_other_detail),
                    placeholder = stringResource(R.string.str_other_detail)
                )
            }
        }
        LoadingOverlay(isLoading = isLoading)
    }
}


@Composable
fun BusinessDetailsToolbar(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.green_700))
            .statusBarsPadding()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
                .size(28.dp)
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(R.string.str_business_detail).uppercase(),
            color = Color.White,
            fontSize = dimensionResource(id = R.dimen.font_size_title).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(28.dp))
    }
}

@Composable
fun BusinessDetailsSaveButtonBar(
    onSaveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(16.dp,0.dp,16.dp,8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        CustomButton(
            text = stringResource(R.string.str_save),
            onClick = onSaveClick,
            backgroundColor = R.color.green_700,
            textColor = R.color.white,
            cornerRadius = 8.dp
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessDetailsPreview() {
    BusinessDetailsScreenContent(
        onBackClick = {}
    )
}
