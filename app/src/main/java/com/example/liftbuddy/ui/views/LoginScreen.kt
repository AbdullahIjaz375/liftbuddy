package com.example.liftbuddy.ui.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.liftbuddy.R
import com.example.liftbuddy.data.repository.UserRepository


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController, userRepository: UserRepository) {

    if (userRepository.isUserLoggedIn()) {
        navController.navigate("dashboardFragment")
        return
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
    ) {
        Image(
            painter = painterResource(id = R.drawable.signintext),
            contentDescription = "sign in text",
            modifier = Modifier
                .offset(x = 130.dp, y = 80.dp)
                .size(150.dp)
        )
        usernameTextField(username = username, onUsernameChange = { username = it })
        if (keyboardController != null) {
            passwordTextField(
                password = password,
                onPasswordChange = { password = it },
                keyboardController = keyboardController,
                offset = 270.dp
            )
        }
        SubmitButton(navController, username, password, userRepository, LocalContext.current)
    }
    Box {
        Text(
            text = "Forgot your Password?",
            fontSize = 10.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFFF8F8F8),
            modifier = Modifier
                .offset(y = 400.dp, x = 150.dp)
                .clickable {
                    navController.navigate("forgotPasswordFragment")
                }
        )
    }

    Box() {
        Text(
            text = "Don’t have an account? Sign Up",
            fontSize = 16.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFFF8F8F8),
            modifier = Modifier
                .offset(y = 500.dp, x = 90.dp)
                .clickable {
                    navController.navigate("signUpFragment")
                }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun usernameTextField(username: String, onUsernameChange: (String) -> Unit) {
    TextField(
        value = username,
        onValueChange = onUsernameChange,
        label = { Text("Name") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .offset(y = 200.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun passwordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController,
    offset: Dp
) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController.hide()
        }),
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = offset)
    )
}

@Composable
fun SubmitButton(
    navController: NavController,
    username: String,
    password: String,
    userRepository: UserRepository,
    context: Context
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 80.dp, y = 350.dp)
            .clickable {
                // Call the signInUser function from UserRepository
                userRepository
                    .signInUser(username, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Navigate to the dashboard on successful login
                            navController.navigate("dashboardFragment")
                            Toast
                                .makeText(context, "Login successful!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            // Handle login failure (show a message, etc.)
                            // For example:
                            Toast
                                .makeText(
                                    context,
                                    "Incorrect Username or Password!",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.submit), // Replace with your image resource
            contentDescription = "Submit Button",
        )
    }
}