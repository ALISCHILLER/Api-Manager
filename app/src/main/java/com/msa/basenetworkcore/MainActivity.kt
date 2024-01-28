package com.msa.basenetworkcore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.msa.basenetworkcore.ui.screen.PiProgressIndicator
import com.msa.basenetworkcore.ui.screen.WeatherMainScreen
import com.msa.basenetworkcore.ui.theme.BaseNetworkCoreTheme
import com.msa.basenetworkcore.ui.theme.Dimen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseNetworkCoreTheme {
                 val viewModel: ApiViewModel = hiltViewModel()
                val homeState by viewModel.homeState.collectAsState()
                // A surface container using the 'background' color from the theme
                if (homeState.isLoading) {
                    PiProgressIndicator()
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    Column {
                        Button(onClick = {
                            viewModel.fetchWeather1()
                        }) {
                            Text(text = "Succses")
                        }

                        Button(onClick = {
                           viewModel.fetchUserData()
                        }) {
                            Text(text = "Error")
                        }
                        Spacer(modifier = Modifier.height(Dimen.doubleSpace))

                        homeState.weatherResponse?.let { WeatherMainScreen(it) }
                    }

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BaseNetworkCoreTheme {

    }
}