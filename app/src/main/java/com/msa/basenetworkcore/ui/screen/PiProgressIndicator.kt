package com.msa.basenetworkcore.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.window.Dialog

@Composable
fun PiProgressIndicator(isDialogIndicator:Boolean = true, modifier: Modifier = Modifier) {
    if (isDialogIndicator) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            PiProgressBar(modifier = modifier)
        }
    } else {
        PiProgressBar(modifier = modifier)
    }
}


@Composable
private fun PiProgressBar(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize().testTag("PiProgressBar")) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}