package com.example.liftbuddy.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.liftbuddy.R
import com.example.liftbuddy.data.repository.UserRepository


@SuppressLint("UnrememberedMutableState")
@Composable
fun SettingScreen(navController: NavHostController, userViewModel: UserRepository) {
    var isDialogShown by mutableStateOf(false)
    var selected by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181922))
    ) {
        Image(
            painter = painterResource(id = R.drawable.settingheader),
            contentDescription = "setting",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.divider),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .offset(y = 50.dp)
                .fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.drawable.settingsbox),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 70.dp)
                .fillMaxHeight(0.19f)
        )


        Image(
            painter = painterResource(id = R.drawable.notifications),
            contentDescription = null,
            modifier = Modifier.offset(x = 15.dp, y = 90.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.changepassword),
            contentDescription = null,
            modifier = Modifier.offset(x = 15.dp, y = 143.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.logout),
            contentDescription = null,
            modifier = Modifier.offset(x = 15.dp, y = 185.dp).clickable{
                navController.navigate("LoginFragment")
                userViewModel.signOutUser()
            }
        )
        Image(
            painter = painterResource(id = R.drawable.lessthan),
            contentDescription = null,
            modifier = Modifier
                .offset(x = 360.dp, y = 140.dp)
                .clickable { navController.navigate("ChangePasswordFragment") }
        )

        Image(
            painter = painterResource(id = R.drawable.lessthan),
            contentDescription = null,
            modifier = Modifier
                .offset(x = 360.dp, y = 185.dp)
                .clickable { TODO() }
        )
        Image(
            painter = painterResource(id = R.drawable.deleteaccount),
            contentDescription = null,
            modifier = Modifier
                .offset(x = 15.dp, y = 270.dp)
                .fillMaxWidth()
                .clickable { TODO() }
        )

        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(x = -(5).dp, y = 70.dp)
                .clickable {
                    selected = !selected
                }
                .align(Alignment.TopEnd)
        ) {
            Image(
                painter = painterResource(id = if (selected) R.drawable.toggle_on else R.drawable.toggle_off),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}



