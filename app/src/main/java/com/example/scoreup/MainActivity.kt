package com.example.scoreup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.scoreup.core.navigation.FeatureNavGraph
import com.example.scoreup.core.navigation.NavigationWrapper
import com.example.scoreup.core.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navGraphs: Set<@JvmSuppressWildcards FeatureNavGraph>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme(){
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { _ ->
                    NavigationWrapper(navGraphs = navGraphs)
                }
            }
        }
    }
}