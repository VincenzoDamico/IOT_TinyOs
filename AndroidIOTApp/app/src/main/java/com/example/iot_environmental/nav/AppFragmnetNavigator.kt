package com.example.iot_environmental.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.iot_environmental.ui.view.model.AuthViewModel
import com.example.iot_environmental.ui.view.screen.HomeScreen
import com.example.iot_environmental.ui.view.screen.LoginScreen
import com.example.iot_environmental.ui.view.screen.RegisterScreen


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