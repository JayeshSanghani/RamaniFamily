package com.ramanifamily.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import com.ramanifamily.R
import com.ramanifamily.common.AppOutlinedDatePickerField
import com.ramanifamily.common.AppOutlinedDropdownField
import com.ramanifamily.common.AppOutlinedTextField
import com.ramanifamily.common.CustomButton
import com.ramanifamily.common.ProfileImagePicker
import com.ramanifamily.common.ToastUtils
import com.ramanifamily.common.Utils
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramanifamily.common.LoadingOverlay
import com.ramanifamily.data.remote.ApiState
import com.ramanifamily.data.remote.AppModule.getDistrictsUseCase
import com.ramanifamily.data.remote.AppModule.getSubDistrictsUseCase
import com.ramanifamily.data.remote.AppModule.networkChecker
import com.ramanifamily.data.remote.AppModule.userDataStoreRepository
import com.ramanifamily.presentation.viewmodel.PersonalDetailsViewModel
import com.ramanifamily.presentation.viewmodel.PersonalDetailsViewModelFactory


@Composable
fun PersonalDetailsScreen(
    navController: NavController) {
    PersonalDetailsScreenContent(
        onBackClick = { navController.navigateUp() }
    )
}

@Composable
fun PersonalDetailsScreenContent(
    onBackClick: () -> Unit
) {
    val view = LocalView.current
    val context = view.context

    var fName by remember { mutableStateOf("") }
    var mName by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var subDistrict by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var currentAddress by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var showNumber by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var selectedMaritalStatus by remember { mutableStateOf("") }
    var donateBlood by remember { mutableStateOf(true) }
    var lastDonatedDate by remember { mutableStateOf("") }
    var selectedBloodGroup by remember { mutableStateOf("") }

    val viewModel: PersonalDetailsViewModel = viewModel(
        factory = PersonalDetailsViewModelFactory(
            userDataStoreRepository,
            getDistrictsUseCase,
            getSubDistrictsUseCase,
            networkChecker
        )
    )
    val userDetails by viewModel.userDetails.collectAsState()

    val districtsState by viewModel.districtsState.collectAsState()
    val subDistrictsState by viewModel.subDistrictsState.collectAsState()
    var selectedDistrictId by remember { mutableStateOf<Int?>(null) }
    var selectedSubDistrictId by remember { mutableStateOf<Int?>(null) }
    val genderList = listOf("Select", "Male", "Female", "Other")
    val maritalStatusList = listOf("Select", "Married", "Unmarried", "Divorced")
    val bloodGroupList = listOf("A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-")

//    var profileImage by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getDistricts(stateId = 1) // Gujarat
    }

    LaunchedEffect(userDetails) {
        userDetails?.let { user ->

            fName = user.user.firstName
            mName = user.user.middleName
            surname = user.user.surname

            district = user.user.districtName
            subDistrict = user.user.subDistrictName
            village = user.user.village
            currentAddress = user.user.address

            dob = user.user.dob
            age = Utils.calculateAge(user.user.dob)

            mobile = user.user.mobile
            showNumber = user.user.showNumber

            email = user.user.email
            selectedGender = user.user.gender
            selectedMaritalStatus = user.user.maritalStatus

            lastDonatedDate = user.user.lastDonated
            selectedBloodGroup = user.user.bloodGroup
            donateBlood = user.user.donateBlood
        }
    }


    Scaffold(
        containerColor = Color.White,

        topBar = {
            PersonalDetailsToolbar(onBackClick)
        },

        bottomBar = {
            SaveButtonBar(
                onSaveClick = {
                    when {
//                        profileImage == null -> ToastUtils.show(context, R.string.str_sel_image)
                        fName.isBlank() -> ToastUtils.show(context, R.string.ent_firstname)
                        mName.isBlank() -> ToastUtils.show(context, R.string.ent_middlename)
                        surname.isBlank() -> ToastUtils.show(context, R.string.ent_surname)
                        district.isBlank() -> ToastUtils.show(context, R.string.sel_district)
                        subDistrict.isBlank() -> ToastUtils.show(context, R.string.sel_sub_district)
                        village.isBlank() -> ToastUtils.show(context, R.string.ent_village)
                        currentAddress.isBlank() -> ToastUtils.show(context, R.string.ent_current_add)
                        dob.isBlank() -> ToastUtils.show(context, R.string.ent_date_of_birth)
                        mobile.isBlank() -> ToastUtils.show(context, R.string.ent_mobile_no)
                        mobile.length != 10 -> ToastUtils.show(context, R.string.ent_mobile)
                        email.isBlank() -> ToastUtils.show(context, R.string.ent_email)
                        !Utils.isValidEmail(email) -> ToastUtils.show(context, R.string.ent_valid_email)
                        selectedGender.isBlank() || selectedGender == "Select" -> ToastUtils.show(context, R.string.str_sel_gender)
                        selectedMaritalStatus.isBlank() || selectedGender == "Select" -> ToastUtils.show(context, R.string.str_sel_marital_status)
                        selectedBloodGroup.isBlank() -> ToastUtils.show(context, R.string.str_sel_blood_group)

                        else -> {
                            val TAG = "PersonalDetails"

                            Log.e(TAG, "First Name: $fName")
                            Log.e(TAG, "Middle Name: $mName")
                            Log.e(TAG, "Surname: $surname")
                            Log.e(TAG, "District: $district")
                            Log.e(TAG, "selectedDistrictId: $selectedDistrictId")
                            Log.e(TAG, "SunDistrict: $subDistrict")
                            Log.e(TAG, "selectedSubDistrictId: $selectedSubDistrictId")
                            Log.e(TAG, "Village: $village")
                            Log.e(TAG, "Current Address: $currentAddress")
                            Log.e(TAG, "DOB: $dob")
                            Log.e(TAG, "Age: $age")
                            Log.e(TAG, "Mobile: $mobile")
                            Log.e(TAG, "Show Number: $showNumber")
                            Log.e(TAG, "Email: $email")
                            Log.e(TAG, "Gender: $selectedGender")
                            Log.e(TAG, "Marital Status: $selectedMaritalStatus")
                            Log.e(TAG, "Donate Blood: $donateBlood")
                            Log.e(TAG, "Last Donated Date: $lastDonatedDate")
                            Log.e(TAG, "Blood Group: $selectedBloodGroup")
//                            Log.e(TAG, "Profile Image Uri: $profileImage")

                            onBackClick()
                        }
                    }
                }

            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {

//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.Center
//            ) {
//                ProfileImagePicker(
//                    imageUri = profileImage,
//                    onImageSelected = { profileImage = it }
//                )
//            }

            Spacer(modifier = Modifier.height(16.dp))

            AppOutlinedTextField(fName, { fName = it }, stringResource(R.string.str_firstname))
            AppOutlinedTextField(mName, { mName = it }, stringResource(R.string.str_middlename))
            AppOutlinedTextField(surname, { surname = it }, stringResource(R.string.str_surname))

            val districtOptions =
                (districtsState as? ApiState.Success)
                    ?.data
                    ?.data
                    ?.map { it.name }
                    ?: emptyList()
            AppOutlinedDropdownField(
                value = district,
                label = stringResource(R.string.sel_district),
                options = districtOptions,
                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                onValueChange = { selectedName ->
                    district = selectedName

                    val selectedDistrict =
                        (districtsState as? ApiState.Success)
                            ?.data
                            ?.data
                            ?.firstOrNull { it.name == selectedName }

                    selectedDistrict?.let {
                        selectedDistrictId = it.id        //SAVE ID
                        viewModel.getSubDistricts(it.id)

                        // reset sub-district
                        subDistrict = ""
                        selectedSubDistrictId = null
                    }
                }
            )

            val subDistrictOptions =
                (subDistrictsState as? ApiState.Success)
                    ?.data
                    ?.data
                    ?.map { it.name }
                    ?: emptyList()
            AppOutlinedDropdownField(
                value = subDistrict,
                label = stringResource(R.string.sel_sub_district),
                options = subDistrictOptions,
                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                onValueChange = { selectedName ->
                    subDistrict = selectedName

                    val selectedSubDistrict =
                        (subDistrictsState as? ApiState.Success)
                            ?.data
                            ?.data
                            ?.firstOrNull { it.name == selectedName }

                    selectedSubDistrict?.let {
                        selectedSubDistrictId = it.id     // SAVE ID
                    }
                }
            )

            AppOutlinedTextField(
                value = village,
                onValueChange = { village = it },
                label = stringResource(R.string.str_village)
            )

//            AppOutlinedDropdownField(
//                value = district,
//                label = stringResource(R.string.sel_district),
//                options = districtList,
//                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill)
//            ) { district = it }
//
//            AppOutlinedDropdownField(
//                value = taluka,
//                label = stringResource(R.string.sel_taluka),
//                options = talukaList,
//                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill)
//            ) { taluka = it }
//
//            AppOutlinedDropdownField(
//                value = village,
//                label = stringResource(R.string.sel_village),
//                options = villageList,
//                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill)
//            ) { village = it }

            AppOutlinedTextField(
                value = currentAddress,
                onValueChange = { currentAddress = it },
                label = stringResource(R.string.str_current_add)
            )

            Row(modifier = Modifier.fillMaxWidth()) {

                AppOutlinedDatePickerField(
                    value = dob,
                    label = stringResource(R.string.str_lbl_date_of_birth),
                    modifier = Modifier.weight(0.8f),
                    disableFutureDates = true
                ) { selectedDate ->
                    dob = selectedDate
                    age = Utils.calculateAge(selectedDate)
                }

                Spacer(modifier = Modifier.width(16.dp))

                AppOutlinedTextField(
                    value = age,
                    onValueChange = {},
                    label = stringResource(R.string.str_age),
                    modifier = Modifier.weight(0.2f),
                    readOnly = true    //  better than enabled=false
                )
            }


            AppOutlinedTextField(
                value = mobile,
                onValueChange = {
                    if (it.length <= 10 && it.all(Char::isDigit)) {
                        mobile = it
                    }
                },
                label = stringResource(R.string.str_mobile_no),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            YesNoRadioGroup(
                title = stringResource(R.string.str_show_number),
                selected = showNumber,
                onSelect = { showNumber = it }
            )

            AppOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.str_email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Row(modifier = Modifier.fillMaxWidth()) {

                AppOutlinedDropdownField(
                    value = selectedGender,
                    label = stringResource(R.string.str_gender),
                    options = genderList,
                    trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                    modifier = Modifier.weight(0.4f)
                ) { selectedGender = it }

                Spacer(modifier = Modifier.width(16.dp))

                AppOutlinedDropdownField(
                    value = selectedMaritalStatus,
                    label = stringResource(R.string.str_marital_status),
                    options = maritalStatusList,
                    trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                    modifier = Modifier.weight(0.6f)
                ) { selectedMaritalStatus = it }
            }

//            AppOutlinedTextField(
//                value = lastDonatedDate,
//                onValueChange = { lastDonatedDate = it },
//                label = stringResource(R.string.str_lbl_date_of_donation)
//            )

            AppOutlinedDropdownField(
                value = selectedBloodGroup,
                label = stringResource(R.string.str_blood_group),
                trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
                options = bloodGroupList
            ) { selectedBloodGroup = it }

            AppOutlinedDatePickerField(
                value = lastDonatedDate,
                label = stringResource(R.string.str_lbl_date_of_donation),
                disableFutureDates = true
            ) { selectedDate ->
                lastDonatedDate = selectedDate
            }

            YesNoRadioGroup(
                title = stringResource(R.string.str_donate_blood),
                selected = donateBlood,
                onSelect = { donateBlood = it }
            )

        }
    }

    val isLoading = districtsState is ApiState.Loading ||
            subDistrictsState is ApiState.Loading

    LoadingOverlay(isLoading = isLoading)
}

@Composable
fun SaveButtonBar(
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

@Composable
fun YesNoRadioGroup(
    title: String,
    selected: Boolean,
    onSelect: (Boolean) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioItem( stringResource(R.string.str_yes), selected) { onSelect(true) }
            Spacer(modifier = Modifier.width(24.dp))
            RadioItem( stringResource(R.string.str_no), !selected) { onSelect(false) }
        }
    }
}

@Composable
fun RadioItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        RadioButton(selected = selected, onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = colorResource(id = R.color.green_700),
                unselectedColor = Color.Gray
            ))
        Text(text)
    }
}

@Composable
fun PersonalDetailsToolbar(
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
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
                .size(28.dp)
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(R.string.str_personal_detail).uppercase(),
            color = Color.White,
            fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(28.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PersonalDetailsPreview() {
//    PersonalDetailsScreenContent {}
}
