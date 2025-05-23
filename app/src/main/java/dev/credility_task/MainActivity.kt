package dev.credility_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.credility_task.navGraph.CredilityTaskNavGraph
import dev.designsystem.theme.CredilityTaskTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CredilityTaskTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CredilityTaskNavGraph()
                }
            }
        }
    }
}
