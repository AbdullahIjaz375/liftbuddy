package com.example.liftbuddy

import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.liftbuddy.data.repository.UserRepository
import com.example.liftbuddy.ui.theme.LiftBuddyTheme
import com.example.liftbuddy.ui.viewmodel.UserViewModel
import com.example.liftbuddy.ui.views.AddNutrition
import com.example.liftbuddy.ui.views.AuthenticationScreen
import com.example.liftbuddy.ui.views.ChangePassword
import com.example.liftbuddy.ui.views.Dashboard
import com.example.liftbuddy.ui.views.ForgotPassword
import com.example.liftbuddy.ui.views.LoginScreen
import com.example.liftbuddy.ui.views.SearchScreen
import com.example.liftbuddy.ui.views.SettingScreen
import com.example.liftbuddy.ui.views.SignUpScreen
import com.example.liftbuddy.ui.views.WelcomeScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM Token", "Token: $token")
                // Save or use the token as needed, for example, associate it with the user.
            } else {
                Log.e("FCM Token", "Failed to get token: ${task.exception}")
            }
        }
        setContent {
            val navController = rememberNavController()
            val userRepository = UserRepository(FirebaseAuth.getInstance())
            val userViewModel = UserViewModel(userRepository)

            LiftBuddyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "loginFragment"
                    ) {
                        composable("loginFragment") {
                            LoginScreen(navController, userRepository)
                        }
                        composable("signUpFragment") {
                            SignUpScreen(navController, userRepository)
                        }
                        composable("welcomeFragment") {
                            WelcomeScreen(navController, userRepository)
                        }
                        composable("dashboardFragment") {
                            Dashboard(navController,  userViewModel)
                        }
                        composable("forgotPasswordFragment") {
                            ForgotPassword(navController)
                        }
                        composable("authenticationScreenFragment") {
                            AuthenticationScreen()
                        }
                        composable("AddNutritionScreenFragment") {
                            AddNutrition(navController)
                        }
                        composable("SearchScreenFragment") {
                            SearchScreen(navController, userViewModel)
                        }
                        composable("settingsFragment") {
                            SettingScreen(navController, userRepository)
                        }
                        composable("ChangePasswordFragment") {
                            ChangePassword(navController)
                        }

                    }
                }
            }
        }
    }
}

