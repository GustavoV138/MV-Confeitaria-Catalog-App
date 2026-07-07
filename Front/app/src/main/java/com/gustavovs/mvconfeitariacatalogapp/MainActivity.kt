package com.gustavovs.mvconfeitariacatalogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.gustavovs.mvconfeitariacatalogapp.data.model.Cake
import com.gustavovs.mvconfeitariacatalogapp.ui.screens.catalog.CatalogScreen
import com.gustavovs.mvconfeitariacatalogapp.ui.screens.editor.CakeEditorScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "catalog") {
                        composable("catalog") {
                            CatalogScreen(
                                onNavigateToAddCake = {
                                    navController.navigate("editor")
                                },
                                onNavigateToEditCake = { cake ->
                                    val gson = Gson()
                                    val cakeJson = gson.toJson(cake)
                                    val encodedJson = URLEncoder.encode(cakeJson, StandardCharsets.UTF_8.toString())
                                    navController.navigate("editor?cake=$encodedJson")
                                }
                            )
                        }
                        composable(
                            route = "editor?cake={cake}",
                            arguments = listOf(
                                navArgument("cake") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                            )
                        ) { backStackEntry ->
                            val cakeJson = backStackEntry.arguments?.getString("cake")
                            val cake = if (cakeJson != null) {
                                val decodedJson = URLDecoder.decode(cakeJson, StandardCharsets.UTF_8.toString())
                                Gson().fromJson(decodedJson, Cake::class.java)
                            } else {
                                null
                            }

                            CakeEditorScreen(
                                cakeToEdit = cake,
                                onNavigateBack = {
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
