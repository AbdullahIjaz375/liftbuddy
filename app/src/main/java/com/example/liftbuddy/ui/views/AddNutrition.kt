package com.example.liftbuddy.ui.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.liftbuddy.R
import com.example.liftbuddy.ui.theme.FontDefinitions


@Composable
fun AddNutrition(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.settings), contentDescription = "settings",
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .clickable {
//                    navController.navigate("settingsFragment")
//                }
//        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.115f),
            horizontalArrangement = Arrangement.SpaceEvenly, // Space the items evenly in the row
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressBar2(
                percentage = 1f,
                number = 100,
                color = Color.Red,
                radius = 20.dp,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.offset(25.dp))
            CircularProgressBar2(
                percentage = 1f,
                number = 100,
                color = Color.Yellow,
                radius = 20.dp,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.width(25.dp))
            CircularProgressBar2(
                percentage = 1f,
                number = 100,
                radius = 20.dp,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.width(25.dp))
            CircularProgressBar2(
                percentage = 1f,
                number = 100,
                color = Color.Cyan,
                radius = 20.dp,
                fontSize = 10.sp,
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
            horizontalArrangement = Arrangement.spacedBy(85.dp)
        ) {
            val items = listOf("Fats", "Cal", "Carbs", "Pro")

            items(items) { item ->
                Text(
                    item,
                    color = Color.White,
                    fontFamily = FontDefinitions.Montserrat,
                    fontSize = 10.sp,
                    modifier = Modifier.offset(y = 80.dp) // Adjust the vertical offset of each Text
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .offset(y = 100.dp)
        ) {
            for (i in 1..4) {
                Container()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Text(
            text = "Add Food Please",
            modifier = Modifier
                .offset(x = 20.dp, y = 170.dp)
                .alpha(0.0f)
                .clickable {
                    navController.navigate("SearchScreenFragment")
                }
        )
        Text(
            text = "Add Food Please",
            modifier = Modifier
                .offset(x = 20.dp, y = 285.dp)
                .alpha(0.0f)
                .clickable {
                    navController.navigate("SearchScreenFragment")
                }
        )
        Text(
            text = "Add Food Please",
            modifier = Modifier
                .offset(x = 20.dp, y = 400.dp)
                .alpha(0.0f)
                .clickable {
                    navController.navigate("SearchScreenFragment")
                }
        )
        Text(
            text = "Add Food Please",
            modifier = Modifier
                .offset(x = 20.dp, y = 515.dp)
                .alpha(0.0f)
                .clickable {
                    navController.navigate("SearchScreenFragment")
                }
        )
        Image(
            painter = painterResource(id = R.drawable.breakfast),
            contentDescription = "breakfast",
            modifier = Modifier
                .offset(x = 10.dp, y = 65.dp)
                .size(width = 120.dp, height = 120.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.lunch),
            contentDescription = "lunch",
            modifier = Modifier
                .offset(x = 10.dp, y = 200.dp)
                .size(width = 80.dp, height = 80.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.dinner),
            contentDescription = "dinner",
            modifier = Modifier
                .offset(x = 10.dp, y = 310.dp)
                .size(width = 90.dp, height = 90.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.snacks),
            contentDescription = "snacks",
            modifier = Modifier
                .offset(x = 10.dp, y = 420.dp)
                .size(width = 100.dp, height = 100.dp)
        )
    }

}

@Composable
fun Container() {
    Image(
        painter = painterResource(id = R.drawable.container),
        contentDescription = "container",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CircularProgressBar2(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        ), label = "idk"
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(
            modifier = Modifier.size(radius * 2f)
        ) {
            drawArc(
                color = color,
                -90f, 360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

