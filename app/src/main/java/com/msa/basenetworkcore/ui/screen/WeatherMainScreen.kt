package com.msa.basenetworkcore.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.msa.basenetworkcore.R
import com.msa.basenetworkcore.remote.model.Main
import com.msa.basenetworkcore.remote.model.Sys
import com.msa.basenetworkcore.remote.model.WeatherItem
import com.msa.basenetworkcore.remote.model.WeatherResponse
import com.msa.basenetworkcore.remote.model.toFahrenheit
import com.msa.basenetworkcore.remote.util.Constant.EMPTY_STRING
import com.msa.basenetworkcore.ui.theme.Dimen
import com.msa.basenetworkcore.util.piShadow

@Preview
@Composable
fun WeatherMainScreen(
    weatherResponse: WeatherResponse = WeatherResponse(
        weather = listOf(
            WeatherItem("02d", main = "Cloud", description = "Mostly cloud")
        ),
        main = Main(temp = 111.2, tempMin = 234.4, tempMax = 255.0, humidity = 60),
        name = "Texas",
        sys = Sys(country = "US")
    )
) {

    Log.d("WeatherMainScreen", "WeatherMainScreen: ")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (weatherResponse.weather?.isNotEmpty() == true) {
            val weatherItem = weatherResponse.weather?.get(0)
            // To render Weather screen
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = weatherItem?.iconUrl(),
                    modifier = Modifier.size(Dimen.image_size),
                    contentDescription = stringResource(R.string.weather_icon)
                )
                Text(
                    text = weatherItem?.main ?: EMPTY_STRING,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimen.space))

        // To render the location information
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = stringResource(id = R.string.weather_icon)
            )
            Text(
                text = "${weatherResponse.name}, ${weatherResponse.sys?.country}",
                style = MaterialTheme.typography.labelMedium,
            )
        }

        // To render main temp & feel like value
        Text(
            text = weatherResponse.main?.temp?.toFahrenheit() ?: EMPTY_STRING,
            style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(
                R.string.feels_like,
                weatherResponse.main?.feelsLike?.toFahrenheit() ?: EMPTY_STRING
            ),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(Dimen.doubleSpace))
        // To render other information
        Row(
            modifier = androidx.compose.ui.Modifier.padding(Dimen.space),
            horizontalArrangement = Arrangement.spacedBy(Dimen.doubleSpace)
        ) {
            PiWidget(
                imageVector = Icons.Default.RunCircle,
                title = stringResource(R.string.humidity),
                actualValue = (weatherResponse.main?.humidity?.toString() ?: "0") + " %",
                modifier = Modifier.weight(1f)

            )
            PiWidget(
                imageVector = Icons.Default.WindPower,
                title = stringResource(R.string.pressure),
                actualValue = weatherResponse.main?.pressure?.toString() + stringResource(R.string.hpa),
                modifier = Modifier.weight(1f)
            )

        }


        Spacer(modifier = Modifier.height(Dimen.doubleSpace))
        Text(
            text = stringResource(R.string.temperature),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Light,
            modifier = androidx.compose.ui.Modifier
                .padding(Dimen.space)
                .align(Alignment.Start)
        )
        Row(
            modifier = androidx.compose.ui.Modifier.padding(Dimen.space),
            horizontalArrangement = Arrangement.spacedBy(Dimen.doubleSpace)
        ) {
            PiWidget(
                imageVector = Icons.Default.WbSunny,
                title = stringResource(R.string.min),
                actualValue = weatherResponse.main?.tempMin?.toFahrenheit() ?: EMPTY_STRING,
                modifier = Modifier.weight(1f)
            )
            PiWidget(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Default.WbSunny,
                title = stringResource(R.string.max),
                actualValue = weatherResponse.main?.tempMax?.toFahrenheit() ?: EMPTY_STRING
            )

        }

    }
}

@Preview
@Composable
fun PiWidget(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Default.Warning,
    title: String = "Humidity",
    actualValue: String = "35"
) {
    Card(modifier = modifier.piShadow()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.space)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = title,
                modifier = Modifier
                    .size(Dimen.image_size)
                    .padding(top = Dimen.space)
            )
            Spacer(modifier = Modifier.height(Dimen.space))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = actualValue,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.outline
            )

        }

    }
}