package com.ramanifamily.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.graphicsLayer
import coil.compose.rememberAsyncImagePainter
import com.ramanifamily.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ParallaxImageSlider(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    if (images.isEmpty()) return
    val pagerState = rememberPagerState(pageCount = { images.size })
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll effect
    LaunchedEffect(pagerState.currentPage) {
        delay(3000)
        val nextPage = (pagerState.currentPage + 1) % images.size
        coroutineScope.launch {
            pagerState.animateScrollToPage(nextPage)
        }
    }

    val cardHorizontalPadding = 12.dp
    val cardHeight = 200.dp
    val cardScale = 0.85f  // scale of side cards
    val maxOffset = 32f    // parallax max shift

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
        ) { page ->

            // scale animation
            val scale by animateFloatAsState(
                targetValue = if (page == pagerState.currentPage) 1f else cardScale,
                animationSpec = tween(durationMillis = 300)
            )

            // parallax offset calculation
            val pageOffset = (pagerState.currentPage - page).toFloat()
            var translationX = pageOffset * maxOffset

            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = cardHorizontalPadding)
                    .height(cardHeight)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = translationX
                    }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = images[page],
                        placeholder = painterResource(R.drawable.no_image),
                        error = painterResource(R.drawable.no_image)
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Dot indicators
        Row(horizontalArrangement = Arrangement.Center) {
            repeat(images.size) { index ->
                DotIndicator(isSelected = pagerState.currentPage == index)
            }
        }
    }
}

@Composable
fun DotIndicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(if (isSelected) 10.dp else 8.dp)
            .clip(CircleShape)
            .background(if (isSelected) colorResource(R.color.green_500) else Color.LightGray)
    )
}
