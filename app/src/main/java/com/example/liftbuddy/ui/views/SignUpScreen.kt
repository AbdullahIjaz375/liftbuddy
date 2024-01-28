package com.example.liftbuddy.ui.views

import android.util.Log
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.liftbuddy.R
import com.example.liftbuddy.data.repository.UserRepository

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(navController: NavHostController, userRepository: UserRepository) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
    ) {
        Image(
            painter = painterResource(id = R.drawable.signuptext),
            contentDescription = "sign in text",
            modifier = Modifier
                .offset(x = 120.dp, y = 80.dp)
                .size(150.dp)
        )
        email(email = email, onEmailChange = { email = it })
        if (keyboardController != null) {
            password(
                password = password,
                onPasswordChange = { password = it },
                keyboardController = keyboardController
            )
        }
        Submit(email, password, navController, userRepository)
    }
    Image(
        painter = painterResource(id = R.drawable.termsandconditions),
        contentDescription = "terms and conditions",
        modifier = Modifier.offset(x = 85.dp, y = 400.dp)
    )
    Box {
        Text(
            text = "Don’t have an account? Sign Up",
            fontSize = 16.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFFF8F8F8),
            modifier = Modifier
                .offset(y = 500.dp, x = 90.dp)
                .clickable {
                    navController.navigate("LoginFragment")
                }

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun email(email: String, onEmailChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .offset(y = 200.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun password(
    password: String,
    onPasswordChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController
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
            .offset(y = 270.dp)
    )
}

@Composable
fun Submit(
    email: String,
    password: String,
    navController: NavHostController,
    userRepository: UserRepository
) {
    Image(
        painter = painterResource(id = R.drawable.submit),
        contentDescription = "Submit Button",
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 350.dp)
            .clickable {
                Log.d("SignUpScreen", "Submit button clicked")
                userRepository.createUserAndRecord(email, password, "Example Name") { user, exception ->
                    if (exception == null) {
                        Log.d("SignUpScreen", "User created successfully: $user")
                        navController.navigate("WelcomeFragment")
                    } else {
                        Log.e("SignUpScreen", "Error creating user: ${exception.message}")
                        // Handle the error case, possibly show a message to the user
                    }
                }
            }
    )
}
