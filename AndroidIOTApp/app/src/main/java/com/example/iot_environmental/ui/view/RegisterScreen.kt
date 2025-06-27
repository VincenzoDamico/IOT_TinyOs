package com.example.iot_environmental.ui.view

import android.content.res.Configuration
import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.iot_environmental.AppNavigator
import com.example.iot_environmental.AuthState
import com.example.iot_environmental.AuthViewModel
import com.example.iot_environmental.R
import com.example.iot_environmental.ui.theme.ErrorColor
import com.example.iot_environmental.ui.theme.IOT_environmentalTheme

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,navController: NavController,authViewModel: AuthViewModel,registerViewModel:RegisterViewModel= viewModel()
) {


    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home") {
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

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sign))
    val progress by animateLottieCompositionAsState(
        isPlaying = true,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.7f
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) {
        RegisterLandscapeLayout(
            modifier,
            composition,
            progress,
            registerViewModel,
            authViewModel,
            authState,
            navController
        )

    }else {
        RegisterPortraitLayout(
            modifier,
            composition,
            progress,
            registerViewModel,
            authViewModel,
            authState,
            navController
        )
    }
}

@Composable
private fun RegisterPortraitLayout(
    modifier: Modifier,
    composition: LottieComposition?,
    progress: Float,
    registerViewModel: RegisterViewModel,
    authViewModel: AuthViewModel,
    authState: State<AuthState?>,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.requiredSize(300.dp),
            composition = composition,
            progress = { progress }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Create an account",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,

            )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = registerViewModel.email.value,
            onValueChange = { registerViewModel.setEmail(it) },
            label = {
                Text(
                    registerViewModel.emailError.value.ifEmpty { "Email" },
                    color = if (registerViewModel.emailError.value.isNotEmpty()) ErrorColor else Color.Unspecified
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
            value = registerViewModel.password.value,
            onValueChange = { registerViewModel.setPassword(it) },
            label = {
                Text(
                    registerViewModel.passwordError.value.ifEmpty { "Password" },
                    color = if (registerViewModel.passwordError.value.isNotEmpty()) Color.Red else Color.Unspecified
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = "Password Icon"
                )
            },
            visualTransformation = if (registerViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    if (registerViewModel.passwordVisible.value) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .requiredSize(48.dp)
                        .padding(16.dp)
                        .clickable { registerViewModel.setPasswordVisible(!registerViewModel.passwordVisible.value) }
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
            value = registerViewModel.passwordCheck.value,
            onValueChange = { registerViewModel.setPasswordCheck(it) },
            label = {
                Text(
                    registerViewModel.passwordCheckError.value.ifEmpty { "Password Confirmation" },
                    color = if (registerViewModel.passwordCheckError.value.isNotEmpty()) Color.Red else Color.Unspecified
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = "Password Icon"
                )
            },
            visualTransformation = if (registerViewModel.passwordCheckVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    if (registerViewModel.passwordCheckVisible.value) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .requiredSize(48.dp)
                        .padding(16.dp)
                        .clickable { registerViewModel.setPasswordCheckVisible(!registerViewModel.passwordCheckVisible.value) }
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
                .padding(horizontal = 90.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = {
                registerViewModel.setEmailError(
                    if (registerViewModel.email.value.isEmpty()) "Email is required" else if (!Patterns.EMAIL_ADDRESS.matcher(
                            registerViewModel.email.value
                        ).matches()
                    ) "Email is not valid" else ""
                )

                registerViewModel.setPasswordError(if (registerViewModel.password.value.isEmpty()) "Password is required" else "")
                registerViewModel.setPasswordCheckError(
                    if (registerViewModel.passwordCheck.value.isEmpty()) "Password confirmation is required" else
                        if (registerViewModel.passwordCheck.value != (registerViewModel.password.value)) "Password do not match" else ""
                )


                if (registerViewModel.emailError.value.isEmpty() && registerViewModel.passwordError.value.isEmpty() && registerViewModel.passwordCheckError.value.isEmpty()) {
                    authViewModel.signup(
                        registerViewModel.email.value,
                        registerViewModel.password.value
                    )
                }
            }, enabled = authState.value != AuthState.Loading


        ) {
            Text(
                text = "Sign up",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Already have an account? ",
                color = if (isSystemInDarkTheme()) Color.White else Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Login!",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }


    }
}

@Composable
private fun RegisterLandscapeLayout(
    modifier: Modifier,
    composition: LottieComposition?,
    progress: Float,
    registerViewModel: RegisterViewModel,
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
                text = "Create an account",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = registerViewModel.email.value,
                onValueChange = { registerViewModel.setEmail(it) },
                label = {
                    Text(
                        registerViewModel.emailError.value.ifEmpty { "Email" },
                        color = if (registerViewModel.emailError.value.isNotEmpty()) ErrorColor else Color.Unspecified
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
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = registerViewModel.password.value,
                onValueChange = { registerViewModel.setPassword(it) },
                label = {
                    Text(
                        registerViewModel.passwordError.value.ifEmpty { "Password" },
                        color = if (registerViewModel.passwordError.value.isNotEmpty()) Color.Red else Color.Unspecified
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                visualTransformation = if (registerViewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        if (registerViewModel.passwordVisible.value) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier
                            .requiredSize(48.dp)
                            .padding(16.dp)
                            .clickable { registerViewModel.setPasswordVisible(!registerViewModel.passwordVisible.value) }
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
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = registerViewModel.passwordCheck.value,
                onValueChange = { registerViewModel.setPasswordCheck(it) },
                label = {
                    Text(
                        registerViewModel.passwordCheckError.value.ifEmpty { "Password Confirmation" },
                        color = if (registerViewModel.passwordCheckError.value.isNotEmpty()) Color.Red else Color.Unspecified
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                visualTransformation = if (registerViewModel.passwordCheckVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        if (registerViewModel.passwordCheckVisible.value) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier
                            .requiredSize(48.dp)
                            .padding(16.dp)
                            .clickable { registerViewModel.setPasswordCheckVisible(!registerViewModel.passwordCheckVisible.value) }
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
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = {
                    registerViewModel.setEmailError(
                        if (registerViewModel.email.value.isEmpty()) "Email is required" else if (!Patterns.EMAIL_ADDRESS.matcher(
                                registerViewModel.email.value
                            ).matches()
                        ) "Email is not valid" else ""
                    )

                    registerViewModel.setPasswordError(if (registerViewModel.password.value.isEmpty()) "Password is required" else "")
                    registerViewModel.setPasswordCheckError(
                        if (registerViewModel.passwordCheck.value.isEmpty()) "Password confirmation is required" else
                            if (registerViewModel.passwordCheck.value != (registerViewModel.password.value)) "Password do not match" else ""
                    )


                    if (registerViewModel.emailError.value.isEmpty() && registerViewModel.passwordError.value.isEmpty() && registerViewModel.passwordCheckError.value.isEmpty()) {
                        authViewModel.signup(
                            registerViewModel.email.value,
                            registerViewModel.password.value
                        )
                    }
                }, enabled = authState.value != AuthState.Loading

            ) {
                Text(
                    text = "Sign up",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Already have an account? ",
                    color = if (isSystemInDarkTheme()) Color.White else Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Login!",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    }
                )
            }

        }
    }
}

