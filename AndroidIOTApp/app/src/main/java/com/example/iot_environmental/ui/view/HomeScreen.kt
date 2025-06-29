// com.example.iot_environmental.ui.view.HomeScreen.kt
package com.example.iot_environmental.ui.view
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iot_environmental.AuthState
import com.example.iot_environmental.AuthViewModel
import com.example.iot_environmental.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier,navController: NavController,authViewModel: AuthViewModel
) {
    val authState = authViewModel.authState.observeAsState()
//names: List<Friend> = emptyList()
    var names = List(1000) { "$it" }
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
    Column(modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Node list",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(vertical = 8.dp).weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = names) { name ->
                Node(
                    name = name
                )
            }
        }
        ElevatedButton( colors=ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), onClick = {
            authViewModel.signout()
        }) {
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)) { Icon(painter = painterResource(id = R.drawable.exiticon), modifier = Modifier.height(25.dp) // Set height same as the text
                .wrapContentWidth(), contentDescription = "Logout", tint = Color.White)
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Text(text = "Sign out", fontSize = 20.sp, color = Color.White)}

        }
    }

}


@Composable
fun Node(name: String, modifier: Modifier = Modifier) {
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
fun CardContent(name: String,isExpanded: Boolean, onToggleExpansion: () -> Unit) {
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
}