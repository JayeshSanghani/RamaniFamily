package com.ramanifamily.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ramanifamily.R
import com.ramanifamily.common.LoadingOverlay
import com.ramanifamily.common.ParallaxImageSlider
import com.ramanifamily.data.entity.MemberListDataItem
import com.ramanifamily.presentation.viewmodel.DashboardViewModel


@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isSearchOpen by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val filteredMembers = uiState.members.filter {
        it.firstName.contains(searchQuery, true) ||
                it.surname.contains(searchQuery, true) ||
                it.relation.contains(searchQuery, true) ||
                it.mobile.contains(searchQuery, true) ||
                it.bloodGroup.contains(searchQuery, true) ||
                it.gender.contains(searchQuery, true)
    }


    //Listen for "member_added" result from AddFamilyMemberScreen
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val added = backStackEntry.savedStateHandle.get<Boolean>("member_added") ?: false
            if (added) {
                viewModel.refreshMembers()
                backStackEntry.savedStateHandle.set("member_added", false)
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            DashboardToolBar(
                navController = navController,
                profileImageUrl = uiState.profileImage,
                isSearchOpen = isSearchOpen,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onSearchClick = { isSearchOpen = true },
                onSearchClose = {
                    isSearchOpen = false
                    searchQuery = ""
                }
            )

        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            ParallaxImageSlider(
                images = uiState.bannerImages,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.str_family_members),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(colorResource(R.color.green_700).copy(alpha = 0.1f))
                        .clickable {
                            navController.navigate("add_family_member")
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        colorFilter = ColorFilter.tint(colorResource(R.color.green_700))
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = stringResource(R.string.str_add),
                        color = colorResource(R.color.green_700),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredMembers) { member ->
                    FamilyMemberItem(member)
                }
            }
        }
        LoadingOverlay(isLoading = uiState.isLoading)
    }
}

@Composable
fun DashboardToolBar(
    navController: NavController,
    profileImageUrl: String,
    isSearchOpen: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onSearchClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.green_700))
            .statusBarsPadding()
            .height(56.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSearchOpen) {
            SearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                onClose = onSearchClose
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(
                    model = profileImageUrl,
                    placeholder = painterResource(R.drawable.profilepic)
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable { navController.navigate("profile") }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = stringResource(R.string.app_name).uppercase(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(R.drawable.ic_search_white),
                contentDescription = "Search",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onSearchClick() }
            )
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClose: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🔍 Search icon
            Image(
                painter = painterResource(R.drawable.ic_search_white),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                colorFilter = ColorFilter.tint(colorResource(R.color.green_700))
            )

            Spacer(Modifier.width(8.dp))

            // ✅ Search input
            BasicTextField(
                value = query,
                onValueChange = {
                    onQueryChange(it)

                    // 👇 HIDE KEYBOARD WHEN TEXT CLEARED
                    if (it.isEmpty()) {
                        keyboardController?.hide()
                    }
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = colorResource(R.color.green_700),
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 2.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = stringResource(R.string.str_search_members) ,
                            color = colorResource(R.color.green_700).copy(alpha = 0.6f),
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            )

            // ❌ Close icon
//            if (query.isNotEmpty()) {
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            keyboardController?.hide()
                            onClose()
                        },
                    colorFilter = ColorFilter.tint(colorResource(R.color.green_700))
                )
//            }
        }
    }
}

@Composable
fun FamilyMemberItem(member: MemberListDataItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Accent Circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.green_700).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = member.firstName.first().toString(),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.green_700)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${member.firstName} ${member.surname}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = member.relation,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            /* Image(
                 painter = painterResource(R.drawable.ic_right),
                 contentDescription = null,
                 modifier = Modifier.size(18.dp)
             )*/
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardPreview() {
//    DashboardScreen(
//        navController = rememberNavController(),
//        viewModel = FakeDashboardViewModel()
//    )
}

