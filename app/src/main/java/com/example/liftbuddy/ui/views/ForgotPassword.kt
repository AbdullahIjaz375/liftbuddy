package com.example.liftbuddy.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.liftbuddy.R

@Composable
fun ForgotPassword(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
    ) {
        Image(
            painter = painterResource(id = R.drawable.forgotpassword),
            contentDescription = "forgot password",
            modifier = Modifier
                .offset(y = 30.dp, x = 50.dp)
                .width(300.dp)
                .height(300.dp)
        )
        ForgotEmail(email = email, onEmailChange = { email = it })
    }
    Box() {
        Image(
            painter = painterResource(id = R.drawable.sendcode),
            contentDescription = "send code button",
            modifier = Modifier
                .offset(x = 75.dp, y = 350.dp)
                .clickable {
                    navController.navigate("authenticationScreenFragment")
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotEmail(email: String, onEmailChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("myemail@gmail.com") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .offset(y = 250.dp)
    )
}



