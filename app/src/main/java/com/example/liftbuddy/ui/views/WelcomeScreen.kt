package com.example.liftbuddy.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.liftbuddy.R
import com.example.liftbuddy.data.entity.User.User
import com.example.liftbuddy.data.repository.UserRepository

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WelcomeScreen(navController: NavHostController, userRepository: UserRepository) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableIntStateOf(0) }
    var height by remember { mutableIntStateOf(0) }
    var timeLimit by remember { mutableIntStateOf(0) }
    var weight by remember { mutableIntStateOf(0) }
    var targetWeight by remember { mutableIntStateOf(0) }
    var gender by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xff181922))) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.welcome),
                    contentDescription = "welcome text",
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                )
            }
            item {
                name(name = name, onNameChange = { name = it })
            }
            item {
                age(age = age, onAgeChange = { age = it })
            }
            item {
                height(height = height, onHeightChange = { height = it })
            }
            item {
                gender(gender = gender, onGenderChange = { gender = it })
            }
            item {
                weight(weight = weight, onWeightChange = { weight = it })
            }
            item {
                target(target = targetWeight, onTargetChange = { targetWeight = it })
            }
            item {
                timeLimit(timeLimit = timeLimit, onTimeLimitChange = { timeLimit = it })
            }
            item {
                SubmitButton(
                    isClickable = true,
                    navController,
                    userRepository,
                    name,
                    age,
                    height,
                    weight,
                    targetWeight,
                    timeLimit,
                    gender
                )
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun name(name: String, onNameChange: (String) -> Unit) {
    Text(text = "What should we call you by?", color = Color.White)
    TextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Name") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun age(age: Int, onAgeChange: (Int) -> Unit) {
    Text(text = "How old are you?", color = Color.White)
    TextField(
        value = age.toString(),
        onValueChange = {
            val intValue = it.toIntOrNull() ?: 0
            onAgeChange(intValue)
        },
        label = { Text("Age") },
        leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun height(height: Int, onHeightChange: (Int) -> Unit) {
    Text(
        text = "How tall are you (CM)?",
        color = Color.White,
    )
    TextField(
        value = height.toString(),
        onValueChange = {
            val IntValue = it.toIntOrNull() ?: 0
            onHeightChange(IntValue)
        },
        label = { Text("Height") },
        leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun weight(weight:Int, onWeightChange: (Int) -> Unit) {
    Text(
        text = "How much do you weigh in KG?",
        color = Color.White,
    )
    TextField(
        value = weight.toString(),
        onValueChange = {
            val IntValue = it.toIntOrNull() ?: 0
            onWeightChange(IntValue)
        },
        label = { Text("Weight") },
        leadingIcon = { Icon(Icons.Default.AddCircle, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun target(target: Int, onTargetChange: (Int) -> Unit) {
    Text(
        text = "What is your target weight?",
        color = Color.White,
    )
    TextField(
        value = target.toString(),
        onValueChange = {
            val IntValue = it.toIntOrNull() ?: 0
            onTargetChange(IntValue)
        },
        label = { Text("Target Weight") },
        leadingIcon = { Icon(Icons.Default.Face, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timeLimit(timeLimit: Int, onTimeLimitChange: (Int) -> Unit) {
    Text(
        text = "When do you want to achieve your goal weight?",
        color = Color.White,
    )
    TextField(
        value = timeLimit.toString(),
        onValueChange = {
            val intValue = it.toIntOrNull() ?: 0
            onTimeLimitChange(intValue)
        },
        label = { Text("Time Limit") },
        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun gender(gender: String, onGenderChange: (String) -> Unit) {
    Text(text = "What should we call you by?", color = Color.White)
    TextField(
        value = gender,
        onValueChange = onGenderChange,
        label = { Text("Gender") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun SubmitButton(
    isClickable: Boolean,
    navController: NavHostController,
    userRepository: UserRepository,
    name: String,
    age: Int,
    height: Int,
    weight: Int,
    target: Int,
    timeLimit: Int,
    gender: String,
) {
    val buttonColor = if (isClickable) Color.Blue else Color.Gray

    Button(
        onClick = {
            // Create a User object with the provided details
            val user = User(
                email = "",  // Email should be provided or fetched from the current user context
                password = "",  // Password is not needed here; assuming it's handled elsewhere
                name = name,
                age = age,
                weight = weight,
                height = height,
                gender = gender,
                targetWeight = target,
                timeLimitInWeeks = timeLimit
                // consumed values are initialized to default
            )

            // Update user information in the database
            userRepository.updateUserAndCalculateGoals(user) { success, exception ->
                if (success) {
                    navController.navigate("loginFragment")  // Navigate to the "LoginFragment"
                } else {
                    // Handle the error, e.g., show an error message to the user
                    Log.e("SubmitButton", "Error updating user: ${exception?.message}")
                }
            }
        },
        modifier = Modifier.fillMaxWidth(0.5f),
        enabled = isClickable,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = Color.White)
    ) {
        Text("Submit")
    }
}

