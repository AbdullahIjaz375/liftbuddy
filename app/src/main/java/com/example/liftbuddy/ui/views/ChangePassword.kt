package com.example.liftbuddy.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.liftbuddy.R
import com.example.liftbuddy.data.repository.UserRepository

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChangePassword(navController: NavHostController, ) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
    ) {
        Image(
            painter = painterResource(id = R.drawable.changepassword),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .offset(y = 50.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.divider),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 95.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.resetpassword),
            contentDescription = "reset password",
            modifier = Modifier.offset(y = 120.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.newpassword),
            contentDescription = "new password",
            modifier = Modifier
                .offset(x = -(5).dp, y = 230.dp)
                .height(15.dp)
                .width(140.dp)
        )
        if (keyboardController != null) {
            passwordTextField(
                password = password1,
                onPasswordChange = { password1 = it },
                keyboardController = keyboardController,
                offset = 150.dp
            )
        }
        if (keyboardController != null) {
            passwordTextField(
                password = password2,
                onPasswordChange = { password2 = it },
                keyboardController = keyboardController,
                offset = 270.dp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.changepasswordbutton),
            contentDescription = null,
            modifier = Modifier
                .offset(y = 380.dp)
                .width(550.dp)
                .height(50.dp)
                .clickable {
                    navController.navigate("loginFragment")
                }
        )
    }


}