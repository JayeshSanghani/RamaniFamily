package com.ramanifamily.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ramanifamily.R
import com.ramanifamily.presentation.viewmodel.ProfileViewModel
import com.ramanifamily.presentation.viewmodel.ProfileViewModelFactory

@Composable
fun ProfileScreen(navController: NavController,
                  viewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory())) {
    ProfileScreenContent(
        onBackClick = { navController.navigateUp() },
        onPersonalClick = { navController.navigate("personal_details") },
        onBusinessClick = { navController.navigate("business_details") },
        onMaritalClick = { navController.navigate("marital_details") },
        onLogoutClick = {
            viewModel.logout {
                navController.navigate("login") {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }
    )
}
@Composable
fun ProfileScreenContent(
    onBackClick: () -> Unit,
    onPersonalClick: () -> Unit,
    onBusinessClick: () -> Unit,
    onMaritalClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        containerColor = Color.White,
        topBar = { ProfileToolbar(onBackClick) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(R.drawable.profilepic),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, colorResource(R.color.green_700), CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "User Name",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(36.dp))

            ProfileOption(
                title = stringResource(R.string.str_personal_detail),
                icon = R.drawable.ic_personal_details,
                onClick = onPersonalClick
            )

            ProfileOption(
                title = stringResource(R.string.str_business_detail),
                icon = R.drawable.ic_business,
                onClick = onBusinessClick
            )

            ProfileOption(
                title = stringResource(R.string.str_marital_detail),
                icon = R.drawable.ic_martial,
                onClick = onMaritalClick
            )

            Spacer(modifier = Modifier.height(32.dp))

            LogoutButton(onClick = onLogoutClick)
        }
    }
}

@Composable
fun ProfileOption(
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = colorResource(R.color.green_700),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(R.drawable.ic_right),
                contentDescription = null,
                tint = colorResource(R.color.green_700)
            )
        }
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEAEA)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.str_logout),
            color = Color.Red,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun ProfileToolbar(
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
            text = stringResource(R.string.str_profile).uppercase(),
            color = Color.White,
            fontSize = dimensionResource(id = R.dimen.font_size_title).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(28.dp))
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        Surface {
            ProfileScreenContent(
                onBackClick = {},
                onPersonalClick = {},
                onBusinessClick = {},
                onMaritalClick = {},
                onLogoutClick = {}
            )
        }
    }
}

