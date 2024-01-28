package com.example.liftbuddy.ui.views

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.liftbuddy.R
import com.example.liftbuddy.ui.theme.FontDefinitions
import com.example.liftbuddy.ui.viewmodel.UserViewModel

//@Preview
@SuppressLint("NewApi")
@Composable
fun Dashboard(navController: NavController, userViewModel: UserViewModel) {
    LaunchedEffect(key1 = Unit) {
        userViewModel.fetchConsumedValuesForCurrentUser()
        userViewModel.fetchRecommendedValuesForCurrentUser()
    }

    // Observe consumed and recommended values
    val consumedValues by userViewModel.consumedValues.observeAsState()
    val recommendedValues by userViewModel.recommendedValues.observeAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
    ) {
        Image(
            painter = painterResource(id = R.drawable.settings),
            contentDescription = "settings",
            modifier = Modifier
                .align(Alignment.TopStart)
                .clickable {
                    navController.navigate("settingsFragment")
                }
        )
        Image(
            painter = painterResource(id = R.drawable.dashboard),
            contentDescription = "Dashboard",
            modifier = Modifier.offset(x = 15.dp, y = 60.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.foodlog),
            contentDescription = "Food Log",
            modifier = Modifier.offset(x = 15.dp, y = 150.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.4f)
                .align(Alignment.Center)
                .offset(y = (-50).dp)
                .background(Color(0xFF2F3142))
        ) {
            // Display progress bars if values are available
            if (consumedValues != null && recommendedValues != null) {
                val caloriesPercentage = calculatePercentage(consumedValues!!.first, recommendedValues!!.first)
                val proteinPercentage = calculatePercentage(consumedValues!!.second, recommendedValues!!.second)
                val carbsPercentage = calculatePercentage(consumedValues!!.third, recommendedValues!!.third)
                val fatPercentage = calculatePercentage(consumedValues!!.fourth, recommendedValues!!.fourth)

                // Update progress bars
                Row(modifier = Modifier.offset(y = 15.dp, x = 110.dp)) {
                    CircularProgressBar(
                        percentage = caloriesPercentage,
                        number = consumedValues!!.first,
                        label = "Calories",
                        color = Color.Blue,
                        radius = 60.dp,
                        strokeWidth = 15.dp
                    )
                }
                Row(modifier = Modifier.offset(y = 200.dp, x = 35.dp)) {
                    CircularProgressBar(
                        percentage = proteinPercentage,
                        number = consumedValues!!.second,
                        label = "Protein",
                        color = Color.Red,
                        radius = 40.dp,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(25.dp))
                    CircularProgressBar(
                        percentage = carbsPercentage,
                        number = consumedValues!!.third,
                        label = "Carbs",
                        color = Color.Yellow,
                        radius = 40.dp,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(25.dp))
                    CircularProgressBar(
                        percentage = fatPercentage,
                        number = consumedValues!!.fourth,
                        label = "Fat",
                        color = Color.Magenta,
                        radius = 40.dp,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .offset(x = 75.dp, y = 560.dp)
            .clickable { navController.navigate("AddNutritionScreenFragment") }
    ) {
        Image(
            painter = painterResource(id = R.drawable.logtodayweight),
            contentDescription = "Weight Log Button",
            modifier = Modifier
                .clickable {
                    navController.navigate("AddNutritionScreenFragment")
                }
        )
    }
}

// Helper function to calculate percentage
private fun calculatePercentage(consumed: Long, recommended: Long): Float {
    return if (recommended > 0) consumed.toFloat() / recommended else 0f
}


@Composable
fun CircularProgressBar(
    percentage: Float,  // This should be a calculated percentage
    number: Long?,      // This is the absolute number from the database
    label: String,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(durationMillis = animDuration, delayMillis = animDelay)
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                -90f, 360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Column {
            Text(
                text = number?.toInt().toString(),  // Display the original number
                color = Color.White,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = 3.dp)
            )
            Text(
                text = label,
                color = Color.White,
                fontFamily = FontDefinitions.Montserrat,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}