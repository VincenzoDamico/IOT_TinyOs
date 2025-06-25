package com.example.iot_environmental

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.iot_environmental.ui.theme.IOT_environmentalTheme
import com.example.iot_environmental.ui.view.AppNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IOT_environmentalTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Scaffold (modifier=modifier ,  containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        AppNavigator(paddingValues = innerPadding)
    }
}






@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    IOT_environmentalTheme {
        MyApp(modifier = Modifier.fillMaxSize())

    }
}
@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun LightPreview() {
    IOT_environmentalTheme {
        MyApp(modifier = Modifier.fillMaxSize())
    }
}