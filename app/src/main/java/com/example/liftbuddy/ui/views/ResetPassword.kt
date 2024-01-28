package com.example.liftbuddy.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.liftbuddy.R

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable

fun ResetPassword() {
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
            .offset(y = 25.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.resetpassword),
            contentDescription = "reset password"
        )
        Image(
            painter = painterResource(id = R.drawable.newpassword),
            contentDescription = "new password",
            modifier = Modifier.offset(y = 50.dp)
        )
        password2(password = password2, onPasswordChange = { password2 = it })
        if (keyboardController != null) {
            passwordReset(
                password = password1,
                onPasswordChange = { password1 = it },
                keyboardController = keyboardController
            )
        }
        Image(
            painter = painterResource(id = R.drawable.enterpasswordagain),
            contentDescription = "new password",
            modifier = Modifier.offset(y = 150.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.reset),
            contentDescription = "new password",
            modifier = Modifier.offset(x = 70.dp, y = 250.dp)
        )
    }
}
@Composable
fun password2(password: String, onPasswordChange: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Enter new password of 8 ~ 32 character") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 80.dp)
    )
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun passwordReset(
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
            .offset(y = 180.dp)
    )
}