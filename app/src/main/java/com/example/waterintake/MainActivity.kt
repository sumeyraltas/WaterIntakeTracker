package com.example.waterintake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.waterintake.presentation.dashboard.DashboardScreen
import com.example.waterintake.presentation.theme.CyanAccent
import com.example.waterintake.presentation.theme.PetrolDarkNavy
import com.example.waterintake.presentation.theme.PetrolSurfaceDark
import com.example.waterintake.presentation.theme.TextSlateSecondary
import com.example.waterintake.presentation.theme.TextWhitePrimary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterIntakeAppRoot()
        }
    }
}

@Composable
fun WaterIntakeAppRoot() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PetrolDarkNavy,
        bottomBar = {
            NavigationBar(
                containerColor = PetrolSurfaceDark
            ) {
                NavigationBarItem(
                    selected = currentRoute == "dashboard",
                    onClick = { navController.navigate("dashboard") },
                    icon = { Icon(Icons.Rounded.Home, contentDescription = "Ana Sayfa") },
                    label = { Text("Ana Sayfa") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanAccent,
                        selectedTextColor = CyanAccent,
                        indicatorColor = PetrolDarkNavy,
                        unselectedIconColor = TextSlateSecondary,
                        unselectedTextColor = TextSlateSecondary
                    )
                )
                NavigationBarItem(
                    selected = currentRoute == "history",
                    onClick = { navController.navigate("history") },
                    icon = { Icon(Icons.Rounded.BarChart, contentDescription = "İstatistikler") },
                    label = { Text("Geçmiş") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanAccent,
                        selectedTextColor = CyanAccent,
                        indicatorColor = PetrolDarkNavy,
                        unselectedIconColor = TextSlateSecondary,
                        unselectedTextColor = TextSlateSecondary
                    )
                )
                NavigationBarItem(
                    selected = currentRoute == "profile",
                    onClick = { navController.navigate("profile") },
                    icon = { Icon(Icons.Rounded.Person, contentDescription = "Profil") },
                    label = { Text("Profil") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanAccent,
                        selectedTextColor = CyanAccent,
                        indicatorColor = PetrolDarkNavy,
                        unselectedIconColor = TextSlateSecondary,
                        unselectedTextColor = TextSlateSecondary
                    )
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "dashboard"
        ) {
            composable("dashboard") {
                DashboardScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToSettings = { navController.navigate("profile") }
                )
            }
            composable("history") {
                com.example.waterintake.presentation.history.HistoryScreen(
                    viewModel = hiltViewModel()
                )
            }
            composable("profile") {
                com.example.waterintake.presentation.profile.ProfileScreen(
                    viewModel = hiltViewModel()
                )
            }
        }
    }
}
