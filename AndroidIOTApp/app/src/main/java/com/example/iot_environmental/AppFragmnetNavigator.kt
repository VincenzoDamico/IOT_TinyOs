package com.example.iot_environmental

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.iot_environmental.ui.view.HomeScreen
import com.example.iot_environmental.ui.view.LoginScreen
import com.example.iot_environmental.ui.view.RegisterScreen


@Composable
fun AppNavigator(modifier: Modifier = Modifier, authViewModel: AuthViewModel){
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            LoginScreen(modifier,navController,authViewModel)
        }
        composable("register"){  RegisterScreen(modifier,navController,authViewModel)  }
        composable("home"){  HomeScreen(modifier,navController,authViewModel) }
    }
}