package com.ramanifamily.presentation.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.ramanifamily.R
import com.ramanifamily.common.DotIndicator
import com.ramanifamily.common.ParallaxImageSlider
import com.ramanifamily.presentation.profile.FamilyMember
import com.ramanifamily.presentation.profile.FamilyMemberStore

@Composable
fun DashboardScreen(navController: NavController) {

    val sliderImages = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            DashboardToolBar(navController) {
                // handle add click
                navController.navigate("add_family_member")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            ParallaxImageSlider(
                images = sliderImages,
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(FamilyMemberStore.members) { member ->
                    FamilyMemberItem(member)
                }
            }


        }
    }
}

@Composable
fun FamilyMemberItem(member: FamilyMember) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.disable)
        ),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberAsyncImagePainter(member.image),
                contentDescription = "Member Image",
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = member.fullName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = member.relation,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun DashboardToolBar(
    navController: NavController,
    onAddClick: () -> Unit  // Add click callback parameter
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

        Image(
            painter = painterResource(id = R.drawable.profilepic),
            contentDescription = "Profile",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable {
                    navController.navigate("profile")
                }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = stringResource(R.string.app_name).uppercase(),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        // Plus icon
        Image(
            painter = painterResource(R.drawable.ic_add), // use your white PNG
            contentDescription = "Add Item",
            modifier = Modifier
                .size(28.dp)
                .clickable { onAddClick() }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(
        navController = rememberNavController()
    )
}
