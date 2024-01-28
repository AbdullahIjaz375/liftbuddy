package com.example.liftbuddy.ui.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.liftbuddy.R
import com.example.liftbuddy.ui.theme.FontDefinitions

@Preview
@Composable
fun AuthenticationScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
            .offset(y = 25.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.authentication),
            contentDescription = "authentication"
        )
        Text(
            text = "If myemail@gmail.com is associated with an active account, we have sent you a verification code. Enter the code to continue",
            fontFamily = FontDefinitions.Montserrat,
            color = Color.Gray,
            modifier = Modifier.offset(y = 30.dp)
        )
        OTPInput()
        Image(
            painter = painterResource(id = R.drawable.submitcode),
            contentDescription = "submit code button",
            modifier = Modifier.offset(y = 225.dp, x = 65.dp)
        )
        Box {
            Image(
                painter = painterResource(id = R.drawable.didntreceivecode),
                contentDescription = "didn't receive code",
                modifier = Modifier
                    .offset(y = 295.dp, x = 80.dp)
                    .clickable {
                        //add
                    }
            )
        }

    }
}

@Composable
fun OTPInput() {
    var otpText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 120.dp)
    ) {
        Text(
            text = "Verification Code",
            fontFamily = FontDefinitions.Montserrat,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.offset(y = (-20).dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF181922)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (index in 0 until 6) {
                OTPDigit(
                    value = otpText.getOrNull(index),
                    onValueChange = { otpText = it },
                    onDone = { onOTPSubmit(otpText, context) }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPDigit(
    value: Char?,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit
) {
    var isFocus by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    TextField(
        value = value?.toString() ?: "",
        onValueChange = { newInput ->
            if (newInput.length <= 1) {
                onValueChange(newInput)
                if (newInput.isNotEmpty()) {
                    // Move focus to the next TextField
                    onDone()
                }
            } else {
                // Handle the case where the user pastes more than one character
                onValueChange(newInput.take(1))
                // Move focus to the next TextField
                onDone()
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                isFocus = true
                focusRequester.requestFocus()
            }
        ),
        maxLines = 1,
        modifier = Modifier
            .width(70.dp)
            .height(70.dp)
            .background(color = Color(0xFF00C9FF))
            .padding(4.dp)
            .focusRequester(focusRequester)
            .focusable()
            .onFocusChanged {
                if (it.isFocused && !isFocus) {
                    isFocus = true
                    keyboardController?.show()
                }
            },
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
    )
}


fun onOTPSubmit(otp: String, context: Context) {
    // Implement your logic when OTP is submitted
    // For example, verify the OTP
    // You can use the otp variable here
}