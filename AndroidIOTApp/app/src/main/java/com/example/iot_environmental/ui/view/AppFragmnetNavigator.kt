package com.example.iot_environmental.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigator( paddingValues: PaddingValues){
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            LoginScreen(navController,paddingValues)
        }
        composable("register"){  RegisterScreen(navController,paddingValues)  }
        composable("home"){  HomeScreen(navController,paddingValues) }
    }
}