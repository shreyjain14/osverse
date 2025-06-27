package me.shreyjain.osverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.shreyjain.osverse.navigation.ArModel
import me.shreyjain.osverse.navigation.Screen
import me.shreyjain.osverse.ui.screens.ArViewerScreen
import me.shreyjain.osverse.ui.screens.ModelSelectionScreen
import me.shreyjain.osverse.ui.theme.OsverseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sample models - replace with your actual models
        val sampleModels = listOf(
            ArModel(
                id = 1,
                name = "FCFS Model",
                modelPath = "models/fcfs.obj"
            ),
            // Add more models here when available
            ArModel(id = 2, name = "Coming Soon 1", modelPath = ""),
            ArModel(id = 3, name = "Coming Soon 2", modelPath = ""),
            ArModel(id = 4, name = "Coming Soon 3", modelPath = ""),
            ArModel(id = 5, name = "Coming Soon 4", modelPath = ""),
            ArModel(id = 6, name = "Coming Soon 5", modelPath = "")
        )

        setContent {
            OsverseTheme {
                var selectedModel by remember { mutableStateOf<ArModel?>(null) }
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.ModelSelection.route
                ) {
                    composable(Screen.ModelSelection.route) {
                        ModelSelectionScreen(
                            models = sampleModels,
                            onModelSelected = { model ->
                                selectedModel = model
                                navController.navigate(Screen.ArViewer.route)
                            }
                        )
                    }

                    composable(Screen.ArViewer.route) {
                        selectedModel?.let { model ->
                            ArViewerScreen(
                                model = model,
                                onBackPressed = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}