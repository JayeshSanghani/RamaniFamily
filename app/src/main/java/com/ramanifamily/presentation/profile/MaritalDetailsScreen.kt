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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ramanifamily.R
import com.ramanifamily.common.AppOutlinedDropdownField
import com.ramanifamily.common.AppOutlinedTextField
import com.ramanifamily.common.CustomButton
import com.ramanifamily.common.LoadingOverlay
import com.ramanifamily.common.ToastUtils
import com.ramanifamily.data.entity.ProfileMaritalRequest
import com.ramanifamily.data.remote.ApiState
import com.ramanifamily.data.remote.AppModule
import com.ramanifamily.presentation.viewmodel.ProfileViewModel
import com.ramanifamily.presentation.viewmodel.ProfileViewModelFactory


@Composable
fun MaritalDetailsScreen(navController: NavController) {
    MaritalDetailsScreenContent(onBackClick = { navController.navigateUp() })
}

@Composable
fun MaritalDetailsScreenContent(onBackClick: () -> Unit) {
    val view = LocalView.current
    val context = view.context

    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var selectedZodiac by remember { mutableStateOf("Select zodiac(રાશિ)") }
    var education by remember { mutableStateOf("") }
    var occupation by remember { mutableStateOf("") }
    var brother by remember { mutableStateOf("") }
    var sister by remember { mutableStateOf("") }
    var maternalDetail by remember { mutableStateOf("") }
    var propertyDetail by remember { mutableStateOf("") }

    val zodiacList = remember {
        listOf(
            "Select zodiac(રાશિ)",
            "Aries (મેષ-અ, લ, ઈ)",
            "Taurus (વૃષભ-બ, વ, ઉ)",
            "Gemini (મિથુન-ક, છ, ઘ)",
            "Cancer (કર્ક- ડ, હ)",
            "Leo (સિંહ-મ, ટ)",
            "Virgo (કન્યા-પ, ઠ, ણ)",
            "Libra (તુલા- ર, ત)",
            "Scorpio (વૃશ્ચિક-ન, ય)",
            "Sagittarius (ધનુ-ભ, ધ, ફ, ઢ)",
            "Capricorn (મકર-ખ, જ)",
            "Aquarius (કુંભ-ગ, સ, શ, ષ)",
            "Pisces (મીન-ડ, ચ, ઝ, થ)"
        )
    }

    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(
            AppModule.userDataStoreRepository,
            AppModule.profilePersonalUseCase,
            AppModule.profileBusinessUseCase,
            AppModule.profileMaritalUseCase,
            AppModule.networkChecker
        )
    )
    val profileMaritalState by viewModel.profileMaritalState.collectAsState()
    val isLoading = profileMaritalState is ApiState.Loading
    val userDetails by viewModel.userDetails.collectAsState()

    LaunchedEffect(userDetails) {
        userDetails?.let { user ->

            height = user.user.height
            weight = user.user.weight
            selectedZodiac = user.user.zodiac
            education = user.user.education
            occupation = user.user.occupation
            brother = user.user.brother.toString()
            sister = user.user.sister.toString()
            maternalDetail = user.user.maternalDetail
            propertyDetail = user.user.propertyDetail
        }
    }


    LaunchedEffect(profileMaritalState) {
        when (profileMaritalState) {
            is ApiState.Success -> {
                ToastUtils.show(context, (profileMaritalState as ApiState.Success).data.message)
                viewModel.resetMaritalState()
                onBackClick()
            }

            is ApiState.Error -> {
                ToastUtils.show(context, (profileMaritalState as ApiState.Error).message)
                viewModel.resetMaritalState()
            }

            else -> Unit
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = { MaritalDetailsToolbar(onBackClick = onBackClick) },
        bottomBar = {
            MaritalDetailsSaveButtonBar(
                onSaveClick = {
                    when {
                        height.isBlank() -> ToastUtils.show(context, R.string.ent_height)
                        weight.isBlank() -> ToastUtils.show(context, R.string.ent_weight)
                        selectedZodiac == "Select zodiac(રાશિ)" -> ToastUtils.show(
                            context,
                            R.string.ent_zodiac_sign
                        )

                        education.isBlank() -> ToastUtils.show(context, R.string.str_ent_education)
                        occupation.isBlank() -> ToastUtils.show(
                            context,
                            R.string.str_ent_occupation
                        )

                        brother.isBlank() -> ToastUtils.show(context, R.string.ent_brother)
                        sister.isBlank() -> ToastUtils.show(context, R.string.ent_sister)
                        maternalDetail.isBlank() -> ToastUtils.show(
                            context,
                            R.string.ent_metarnal_detail
                        )

                        propertyDetail.isBlank() -> ToastUtils.show(context, R.string.ent_land)

                        else -> {
                            val TAG = "MaritalDetails"

                            Log.e(TAG, "Height: $height")
                            Log.e(TAG, "Weight: $weight")
                            Log.e(TAG, "Zodiac: $selectedZodiac")
                            Log.e(TAG, "Education: $education")
                            Log.e(TAG, "Occupation: $occupation")
                            Log.e(TAG, "Brother: $brother")
                            Log.e(TAG, "Sister: $sister")
                            Log.e(TAG, "Maternal Detail: $maternalDetail")
                            Log.e(TAG, "Property Detail: $propertyDetail")

                            val request = ProfileMaritalRequest(
                                education = education,
                                occupation = occupation,
                                zodiac = selectedZodiac,
                                propertyDetail = propertyDetail,
                                weight = weight,
                                maternalDetail = maternalDetail,
                                brother = brother.toInt(),
                                sister = sister.toInt(),
                                height = height,
                            )
                            viewModel.maritalState(request)
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
                    .padding(16.dp)
            ) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    AppOutlinedTextField(
                        value = height,
                        onValueChange = {
                            if (it.length <= 3 && it.all(Char::isDigit)) height = it
                        },
                        label = stringResource(R.string.str_height),
                        placeholder = stringResource(R.string.str_hint_height),
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    AppOutlinedTextField(
                        value = weight,
                        onValueChange = {
                            if (it.length <= 3 && it.all(Char::isDigit)) weight = it
                        },
                        label = stringResource(R.string.str_weight),
                        placeholder = stringResource(R.string.str_hint_weight),
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                AppOutlinedDropdownField(
                    value = selectedZodiac,
                    label = stringResource(R.string.str_zodiac_sign),
                    options = zodiacList,
                    trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                    onValueChange = { selectedZodiac = it }
                )

                AppOutlinedTextField(
                    value = education,
                    onValueChange = { education = it },
                    label = stringResource(R.string.str_education),
                    placeholder = stringResource(R.string.str_education)
                )

                AppOutlinedTextField(
                    value = occupation,
                    onValueChange = { occupation = it },
                    label = stringResource(R.string.str_occupation),
                    placeholder = stringResource(R.string.str_occupation)
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    AppOutlinedTextField(
                        value = brother,
                        onValueChange = {
                            if (it.length <= 2 && it.all(Char::isDigit)) brother = it
                        },
                        label = stringResource(R.string.str_brother),
                        placeholder = stringResource(R.string.str_brother),
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    AppOutlinedTextField(
                        value = sister,
                        onValueChange = {
                            if (it.length <= 2 && it.all(Char::isDigit)) sister = it
                        },
                        label = stringResource(R.string.str_sister),
                        placeholder = stringResource(R.string.str_sister),
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                AppOutlinedTextField(
                    value = maternalDetail,
                    onValueChange = { maternalDetail = it },
                    label = stringResource(R.string.str_metarnal_detail),
                    placeholder = stringResource(R.string.ent_metarnal_detail)
                )

                AppOutlinedTextField(
                    value = propertyDetail,
                    onValueChange = { propertyDetail = it },
                    label = stringResource(R.string.str_land),
                    placeholder = stringResource(R.string.ent_land)
                )
            }
            LoadingOverlay(isLoading = isLoading)
        }
    }
}

@Composable
fun MaritalDetailsToolbar(onBackClick: () -> Unit) {
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
            text = stringResource(R.string.str_marital_detail).uppercase(),
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
fun MaritalDetailsSaveButtonBar(
    onSaveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(16.dp, 0.dp, 16.dp, 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        CustomButton(
            text = stringResource(R.string.str_save),
            onClick = onSaveClick,
            backgroundColor = R.color.green_700,
            textColor = R.color.white,
            cornerRadius = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MaritalDetailsPreview() {
    MaritalDetailsScreenContent(onBackClick = {})
}
