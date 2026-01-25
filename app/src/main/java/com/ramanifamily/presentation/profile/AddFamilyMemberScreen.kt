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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramanifamily.R
import com.ramanifamily.common.AppOutlinedDropdownField
import com.ramanifamily.common.AppOutlinedTextField
import com.ramanifamily.common.CustomButton
import com.ramanifamily.common.LoadingOverlay
import com.ramanifamily.common.ToastUtils
import com.ramanifamily.data.entity.AddMemberRequest
import com.ramanifamily.data.remote.ApiState
import com.ramanifamily.data.remote.AppModule
import com.ramanifamily.presentation.viewmodel.AddMemberViewModel

@Composable
fun AddFamilyMemberScreen(
    navController: NavController
) {
    AddFamilyMemberScreenContent(
        navController = navController,
        onBackClick = { navController.navigateUp() }
    )
}

@Composable
fun AddFamilyMemberScreenContent(
    navController: NavController,
    onBackClick: () -> Unit,
    isPreview: Boolean = false
) {
    val view = if (!isPreview) LocalView.current else null
    val context = view?.context

    var firstName by remember { mutableStateOf("") }
    var surName by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var selectedBloodGroup by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val viewModel = remember {
        AddMemberViewModel(addMemberUseCase = AppModule.addMemberUseCase, networkChecker = AppModule.networkChecker)
    }

    val addMemberState by viewModel.addMemberState.collectAsState()
    LaunchedEffect(addMemberState) {
        when (addMemberState) {
            is ApiState.Success -> {

                //Notify Dashboard that member added
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("member_added", true)

                viewModel.resetState()
                navController.popBackStack()
            }

            is ApiState.Error -> {
                ToastUtils.show(context!!, (addMemberState as ApiState.Error).message)
                viewModel.resetState()
            }

            else -> Unit
        }
    }

    val isLoading = addMemberState is ApiState.Loading

    Box(modifier = Modifier.fillMaxSize()) {
    Scaffold(
        containerColor = Color.White,

        topBar = {
            AddFamilyMemberToolbar(onBackClick)
        },

        bottomBar = {
            AddFamilyMemberSaveButtonBar(
                onSaveClick = {
                    if (context == null) return@AddFamilyMemberSaveButtonBar

                    when {
                        firstName.isBlank() ->
                            ToastUtils.show(context, R.string.ent_firstname)

                        surName.isBlank() ->
                            ToastUtils.show(context, R.string.ent_surname)

                        relationship.isBlank() ->
                            ToastUtils.show(context, R.string.str_relation)

                        selectedGender.isBlank() || selectedGender == "Select" ->
                            ToastUtils.show(context, R.string.str_sel_gender)

                        mobile.length != 10 ->
                            ToastUtils.show(context, R.string.ent_mobile)

                        selectedBloodGroup.isBlank() ->
                            ToastUtils.show(context, R.string.str_sel_blood_group)

                        else -> {

                            val TAG = "AddFamilyMemberScreen"
                            Log.e(TAG, firstName)
                            Log.e(TAG, surName)
                            Log.e(TAG, relationship)
                            Log.e(TAG, selectedGender)
                            Log.e(TAG, mobile)
                            Log.e(TAG, selectedBloodGroup)
                            Log.e(TAG, notes)

                            val request = AddMemberRequest(
                                firstName = firstName,
                                surname = surName,
                                relation = relationship,
                                gender = selectedGender,
                                mobile = mobile,
                                bloodGroup = selectedBloodGroup,
                                notes = notes
                            )

                            viewModel.addMember(request)

                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        AddFamilyMemberForm(
            modifier = Modifier.padding(innerPadding),
            firstName = firstName,
            onFirstNameChange = { firstName = it },
            surName = surName,
            onSurNameChange = { surName = it },
            relationship = relationship,
            onRelationshipChange = { relationship = it },
            selectedGender = selectedGender,
            onGenderChange = { selectedGender = it },
            mobile = mobile,
            onMobileChange = { mobile = it },
            selectedBloodGroup = selectedBloodGroup,
            onBloodGroupChange = { selectedBloodGroup = it },
            notes = notes,
            onNotesChange = { notes = it }
        )
    }

    val isLoading = addMemberState is ApiState.Loading
    LoadingOverlay(isLoading = isLoading)
    }
}

@Composable
fun AddFamilyMemberForm(
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    surName: String,
    onSurNameChange: (String) -> Unit,
    relationship: String,
    onRelationshipChange: (String) -> Unit,
    selectedGender: String,
    onGenderChange: (String) -> Unit,
    mobile: String,
    onMobileChange: (String) -> Unit,
    selectedBloodGroup: String,
    onBloodGroupChange: (String) -> Unit,
    notes: String,
    onNotesChange: (String) -> Unit
) {
    val genderList = listOf("Select", "Male", "Female", "Other")
    val relationList = listOf("Father", "Mother", "Brother", "Sister", "Spouse", "Son", "Daughter")
    val bloodGroupList = listOf("A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {


        Spacer(modifier = Modifier.height(16.dp))

        AppOutlinedTextField(firstName, onFirstNameChange, stringResource(R.string.str_firstname))
        AppOutlinedTextField(surName, onSurNameChange, stringResource(R.string.str_surname))

        AppOutlinedDropdownField(
            value = relationship,
            label = stringResource(R.string.str_relation),
            options = relationList,
            trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
            onValueChange = onRelationshipChange
        )

        AppOutlinedDropdownField(
            value = selectedGender,
            label = stringResource(R.string.str_gender),
            options = genderList,
            trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
            onValueChange = onGenderChange
        )

        AppOutlinedTextField(
            value = mobile,
            onValueChange = {
                if (it.length <= 10 && it.all(Char::isDigit)) {
                    onMobileChange(it)
                }
            },
            label = stringResource(R.string.str_mobile_no),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        AppOutlinedDropdownField(
            value = selectedBloodGroup,
            label = stringResource(R.string.str_blood_group),
            options = bloodGroupList,
            trailingIcon = painterResource(R.drawable.ic_down_arrow_fill),
            onValueChange = onBloodGroupChange
        )

        AppOutlinedTextField(notes, onNotesChange, stringResource(R.string.str_notes))

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun AddFamilyMemberToolbar(
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
            text = stringResource(R.string.str_add_family_member).uppercase(),
            color = Color.White,
            fontSize = dimensionResource(R.dimen.font_size_title).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(28.dp))
    }
}

@Composable
fun AddFamilyMemberSaveButtonBar(
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
            cornerRadius = 8.dp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddFamilyMemberPreview() {
    androidx.compose.material3.Surface {
        AddFamilyMemberScreenContent(
            navController = rememberNavController(),
            onBackClick = {},
            isPreview = true
        )
    }
}
