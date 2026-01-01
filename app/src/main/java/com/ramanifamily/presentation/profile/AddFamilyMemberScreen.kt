package com.ramanifamily.presentation.profile

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.ramanifamily.common.*

/*----------------------------------------------------*/
/* ENTRY */
/*----------------------------------------------------*/
@Composable
fun AddFamilyMemberScreen(
    navController: NavController
) {
    AddFamilyMemberScreenContent(
        onBackClick = { navController.navigateUp() }
    )
}

/*----------------------------------------------------*/
/* SCREEN + STATE */
/*----------------------------------------------------*/
@Composable
fun AddFamilyMemberScreenContent(
    onBackClick: () -> Unit,
    isPreview: Boolean = false
) {
    val view = if (!isPreview) LocalView.current else null
    val context = view?.context

    var profileImage by remember { mutableStateOf<Uri?>(null) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var selectedBloodGroup by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

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
                        profileImage == null ->
                            ToastUtils.show(context, R.string.str_sel_image)

                        firstName.isBlank() ->
                            ToastUtils.show(context, R.string.ent_firstname)

                        lastName.isBlank() ->
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
                            FamilyMemberStore.members.add(
                                FamilyMember(
                                    image = profileImage,
                                    fullName = "$firstName $lastName",
                                    relation = relationship
                                )
                            )
                            onBackClick()
                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        AddFamilyMemberForm(
            modifier = Modifier.padding(innerPadding),
            profileImage = profileImage,
            onImageChange = { profileImage = it },
            firstName = firstName,
            onFirstNameChange = { firstName = it },
            lastName = lastName,
            onLastNameChange = { lastName = it },
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
}

/*----------------------------------------------------*/
/* FORM ONLY (NO SCAFFOLD) */
/*----------------------------------------------------*/
@Composable
fun AddFamilyMemberForm(
    modifier: Modifier = Modifier,
    profileImage: Uri?,
    onImageChange: (Uri?) -> Unit,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
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

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            ProfileImagePicker(
                imageUri = profileImage,
                onImageSelected = onImageChange
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        AppOutlinedTextField(firstName, onFirstNameChange, stringResource(R.string.str_firstname))
        AppOutlinedTextField(lastName, onLastNameChange, stringResource(R.string.str_surname))

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

/*----------------------------------------------------*/
/* TOOLBAR */
/*----------------------------------------------------*/
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

/*----------------------------------------------------*/
/* SAVE BUTTON */
/*----------------------------------------------------*/
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

/*----------------------------------------------------*/
/* PREVIEW */
/*----------------------------------------------------*/
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddFamilyMemberPreview() {
    androidx.compose.material3.Surface {
        AddFamilyMemberScreenContent(
            onBackClick = {},
            isPreview = true
        )
    }
}
