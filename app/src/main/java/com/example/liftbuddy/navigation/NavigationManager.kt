package com.example.liftbuddy.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.example.liftbuddy.R

class NavigationManager(private val navController: NavController) {

    fun navigateToLogin() {
        navController.navigate("loginFragment")
    }

    fun navigateToSignUp() {
        navController.navigate("signUpFragment")
    }

    fun navigateToWelcome() {
        navController.navigate("welcomeFragment")
    }

    fun navigateToDashboard() {
        navController.navigate("dashboardFragment")
    }

    fun navigateToForgotPassword() {
        navController.navigate("forgotPasswordFragment")
    }
}
