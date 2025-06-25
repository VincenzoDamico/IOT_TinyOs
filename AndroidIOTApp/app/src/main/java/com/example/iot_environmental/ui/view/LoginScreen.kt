package com.example.iot_environmental.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.iot_environmental.R
import com.example.iot_environmental.ui.theme.ErrorColor

@Composable
fun LoginScreen(navController: NavController, paddingValues: PaddingValues){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login))
    val progress by animateLottieCompositionAsState(isPlaying = true, composition = composition, iterations = LottieConstants.IterateForever, speed = 0.7f)


    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp), // Your other screen-specific padding,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.requiredSize(300.dp),
            composition = composition,
            progress = { progress }
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = email,
            onValueChange = { email = it },
            label = { Text(emailError.ifEmpty { "Email" },
                color=if (emailError.isNotEmpty()) ErrorColor else Color.Unspecified
            )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon"
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                )
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = password,
            onValueChange = { password = it },
            label = { Text(passwordError.ifEmpty { "Password" },
                color=if (passwordError.isNotEmpty()) Color.Red else Color.Unspecified
            )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = "Password Icon"
                )
            },
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon ={
                Icon(
                    if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .requiredSize(48.dp).padding(16.dp)
                        .clickable { passwordVisible = !passwordVisible }
                )


            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 90.dp),
            colors = ButtonDefaults.buttonColors( containerColor = MaterialTheme.colorScheme.primary),
            onClick = {
                emailError= if (email.isEmpty()) "Email is required" else ""
                passwordError= if (password.isEmpty()) "Password is required" else ""
                if (emailError.isEmpty() && passwordError.isEmpty()) {

                    navController.navigate("home")

                }
            }
        ) {
            Text(text="Login",
                color= Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(){
            Text(text="Not a member? ",
                color=if (isSystemInDarkTheme()) Color.White else Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(text="Sing up now!",
                color= MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                }
            )
        }



    }
}