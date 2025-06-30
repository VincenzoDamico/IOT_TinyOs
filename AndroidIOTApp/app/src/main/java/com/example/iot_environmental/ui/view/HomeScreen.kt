// com.example.iot_environmental.ui.view.HomeScreen.kt
package com.example.iot_environmental.ui.view

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iot_environmental.AuthState
import com.example.iot_environmental.AuthViewModel
import com.example.iot_environmental.R

import androidx.lifecycle.viewmodel.compose.viewModel // For ViewModel injection
import com.example.iot_environmental.ui.data.BaseStationData
import com.example.iot_environmental.ui.data.NodeData

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(), // Provide default instance for preview/testing
    firebaseViewModel: FirebaseViewModel = viewModel() // Inject FirebaseViewModel
) {
    val authState = authViewModel.authState.observeAsState()
    val baseStationsData by firebaseViewModel.allBaseStationsData.observeAsState(initial = emptyList()) // Observe Firebase data

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login") {
                popUpTo("home") {
                    inclusive = true
                }
            }
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {  Text(
                text = "Node List",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
            ) }
            if (baseStationsData.isEmpty()) {
                item {
                    Text(text = "No data available from Firebase yet.", color = if (isSystemInDarkTheme())Color.White else Color.Black)
                }
            } else {
                items(baseStationsData) { baseStation ->
                    BaseStationCard(baseStation = baseStation)
                    Spacer(modifier = Modifier.height(16.dp)) // Spacer between base stations
                }
            }
        }

        ElevatedButton(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = {
                authViewModel.signout()
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.exiticon),
                    modifier = Modifier
                        .height(25.dp)
                        .wrapContentWidth(),
                    contentDescription = "Logout",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Text(text = "Sign out", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun BaseStationCard(baseStation: BaseStationData, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Base Station: ${baseStation.baseStationId}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (baseStation.nodes.isEmpty()) {
                Text(text = "No nodes found for this base station.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                baseStation.nodes.forEach { node ->
                    NodeDataCard(node = node)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun NodeDataCard(node: NodeData, modifier: Modifier = Modifier) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = if (isExpanded)
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        else
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Node ID: ${node.nodeId}",
                    style = MaterialTheme.typography.titleMedium,
                    color =if (!isExpanded) MaterialTheme.colorScheme.onPrimaryContainer else  MaterialTheme.colorScheme.onSecondaryContainer
                )
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Filled.ExpandLess else Filled.ExpandMore,
                        contentDescription = if (isExpanded) stringResource(R.string.show_less) else stringResource(R.string.show_more),
                        tint = if (!isExpanded) MaterialTheme.colorScheme.onPrimaryContainer else  MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Timestamp: ${node.timestamp}",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Temperature: ${node.reading.temperature}°C",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Humidity: ${node.reading.humidity}%",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Luminosity: ${node.reading.luminosity}",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

