package com.example.iot_environmental.ui.view.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.iot_environmental.R
import com.example.iot_environmental.ui.theme.ErrorColor
import com.example.iot_environmental.ui.view.model.AuthState
import com.example.iot_environmental.ui.view.model.AuthViewModel
import com.example.iot_environmental.ui.view.model.LogicViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel, logicViewModel: LogicViewModel = viewModel()){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login))
    val progress by animateLottieCompositionAsState(isPlaying = true, composition = composition, iterations = LottieConstants.IterateForever, speed = 0.7f)
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home"){
                popUpTo("register") {
                    inclusive = true
                }
                popUpTo("login") {
                    inclusive = true
                }
            }
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) {
        LoginLandscapeLayout(
            modifier,
            composition,
            progress,
            logicViewModel,
            authViewModel,
            authState,
            navController
        )
    }
    else{
        LoginPortraitLayout(
            modifier,
            composition,
            progress,
            logicViewModel,
            authViewModel,
            authState,
            navController
        )

    }
}

@Composable
private fun LoginPortraitLayout(
    modifier: Modifier,
    composition: LottieComposition?,
    progress: Float,
    logicViewModel: LogicViewModel,
    authViewModel: AuthViewModel,
    authState: State<AuthState?>,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // Your other screen-specific padding,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.requiredSize(300.dp),
            composition = composition,
            progress = { progress }
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = logicViewModel.email.value,
            onValueChange = { logicViewModel.setEmail(it) },
            label = {
                Text(
                    logicViewModel.emailError.value.ifEmpty { "Email" },
                    color = if (logicViewModel.emailError.value.isNotEmpty()) ErrorColor else Color.Unspecified
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon"
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                )
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = logicViewModel.password.value,
            onValueChange = { logicViewModel.setPassword(it) },
            label = {
                Text(
                    logicViewModel.passwordError.value.ifEmpty { "Password" },
                    color = if (logicViewModel.passwordError.value.isNotEmpty()) Color.Red else Color.Unspecified
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = "Password Icon"
                )
            },
            visualTransformation = if (logicViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    if (logicViewModel.passwordVisible.value) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .requiredSize(48.dp)
                        .padding(16.dp)
                        .clickable { logicViewModel.setPasswordVisible(!logicViewModel.passwordVisible.value) }
                )


            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = {
                logicViewModel.setEmailError(if (logicViewModel.email.value.isEmpty()) "Email is required" else "")
                logicViewModel.setPasswordError(if (logicViewModel.password.value.isEmpty()) "Password is required" else "")
                if (logicViewModel.emailError.value.isEmpty() && logicViewModel.passwordError.value.isEmpty()) {
                    authViewModel.login(logicViewModel.email.value, logicViewModel.password.value)
                }
            }, enabled = authState.value != AuthState.Loading
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            Text(
                text = "Not a member? ",
                color = if (isSystemInDarkTheme()) Color.White else Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Sing up now!",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                }
            )
        }
    }
}

@Composable
private fun LoginLandscapeLayout(
    modifier: Modifier,
    composition: LottieComposition?,
    progress: Float,
    logicViewModel: LogicViewModel,
    authViewModel: AuthViewModel,
    authState: State<AuthState?>,
    navController: NavController
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            modifier = Modifier.requiredSize(300.dp),
            composition = composition,
            progress = { progress }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Login",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = logicViewModel.email.value,
                onValueChange = { logicViewModel.setEmail(it) },
                label = {
                    Text(
                        logicViewModel.emailError.value.ifEmpty { "Email" },
                        color = if (logicViewModel.emailError.value.isNotEmpty()) ErrorColor else Color.Unspecified
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email Icon"
                    )
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = logicViewModel.password.value,
                onValueChange = { logicViewModel.setPassword(it) },
                label = {
                    Text(
                        logicViewModel.passwordError.value.ifEmpty { "Password" },
                        color = if (logicViewModel.passwordError.value.isNotEmpty()) Color.Red else Color.Unspecified
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                visualTransformation = if (logicViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        if (logicViewModel.passwordVisible.value) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier
                            .requiredSize(48.dp)
                            .padding(16.dp)
                            .clickable { logicViewModel.setPasswordVisible(!logicViewModel.passwordVisible.value) }
                    )


                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = {
                    logicViewModel.setEmailError(if (logicViewModel.email.value.isEmpty()) "Email is required" else "")
                    logicViewModel.setPasswordError(if (logicViewModel.password.value.isEmpty()) "Password is required" else "")
                    if (logicViewModel.emailError.value.isEmpty() && logicViewModel.passwordError.value.isEmpty()) {
                        authViewModel.login(
                            logicViewModel.email.value,
                            logicViewModel.password.value
                        )
                    }
                }, enabled = authState.value != AuthState.Loading
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                )

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Not a member? ",
                    color = if (isSystemInDarkTheme()) Color.White else Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Sing up now!",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("register")
                    }
                )
            }

        }

    }
}