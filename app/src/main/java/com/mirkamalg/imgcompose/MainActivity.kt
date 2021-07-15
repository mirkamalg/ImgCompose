package com.mirkamalg.imgcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirkamalg.imgcompose.ui.home.Home
import com.mirkamalg.imgcompose.ui.theme.ImgComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImgComposeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar {
                            Text(
                                text = getString(R.string.app_name), modifier = Modifier.padding(
                                    PaddingValues(start = 8.dp)
                                )
                            )
                        }
                    }
                ) {
                    Home()
                }
            }
        }
    }
}