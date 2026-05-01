package com.example.cryptocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.cryptocompose.presentation.navigation.NavigationComponent
import com.example.cryptocompose.presentation.ui.theme.CryptoComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var coinSyncScheduler: CoinSyncScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //coinSyncScheduler.schedulePeriodicSync()

        setContent {
            CryptoComposeTheme {
                Surface (color = MaterialTheme.colorScheme.background) {
                    NavigationComponent()
                }
            }
        }

    }


}