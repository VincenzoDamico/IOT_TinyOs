package com.example.iot_environmental.ui.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iot_environmental.AuthState
import com.example.iot_environmental.AuthViewModel
import com.example.iot_environmental.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier,navController: NavController,authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login") {
                popUpTo("home") {
                    inclusive = true
                }
            }
            else -> Unit
        }
    }

    Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
    Text(text = "Home Page", fontSize = 32.sp)

    TextButton(onClick = {
        authViewModel.signout()
    }) {
        Text(text = "Sign out")
    }
}

}
    /*    navController: NavController,
        paddingValues: PaddingValues,
        names: List<String> = List(1000) { "$it" }
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(items = names)  { name ->
                Node(
                    name = name
              )
            }
        }
    }
    /*
    @Composable
    private fun Node(name: String, modifier: Modifier = Modifier) {
        var isExpanded by rememberSaveable { mutableStateOf(false) }

        Card(
            colors =if (isExpanded)
                CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
            else
                CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),

            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            CardContent(name, isExpanded = isExpanded, onToggleExpansion = {isExpanded = !isExpanded})
        }
    }



    @Composable
    private fun CardContent(name: String,isExpanded: Boolean, onToggleExpansion: () -> Unit) {
        Row(

            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(text = "Hello, ", color= Color.White)
                Text(
                    text = name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color= Color.White
                    )
                )
                if (isExpanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                        color= Color.White

                    )
                }
            }
            IconButton(onClick = onToggleExpansion ){
                Icon(
                    tint= Color.White,
                    imageVector = if (isExpanded) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if (isExpanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
    }*/