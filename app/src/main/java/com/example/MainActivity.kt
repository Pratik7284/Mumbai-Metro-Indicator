package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.metromumbai.data.AppScreen
import com.example.metromumbai.data.BottomNavTab
import com.example.metromumbai.data.MetroViewModel
import com.example.metromumbai.ui.components.AiAssistantDialog
import com.example.metromumbai.ui.components.AppBottomNavBar
import com.example.metromumbai.ui.components.AppToastNotification
import com.example.metromumbai.ui.components.AppTopBar
import com.example.metromumbai.ui.screens.HomeScreen
import com.example.metromumbai.ui.screens.JourneyDetailsScreen
import com.example.metromumbai.ui.screens.MetroMapScreen
import com.example.metromumbai.ui.screens.MoreScreen
import com.example.metromumbai.ui.screens.RoutesScreen
import com.example.metromumbai.ui.screens.SavedScreen
import com.example.metromumbai.ui.screens.ServiceAlertsScreen
import com.example.metromumbai.ui.screens.SplashScreen
import com.example.metromumbai.ui.screens.StationDetailsScreen
import com.example.metromumbai.ui.screens.StationSearchScreen
import com.example.ui.theme.MetroMumbaiTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val viewModel: MetroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MetroMumbaiTheme {
                MetroMumbaiApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MetroMumbaiApp(viewModel: MetroViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var showAiAssistant by remember { mutableStateOf(false) }

    // Auto-dismiss toast notification after 2.5 seconds
    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            delay(2500)
            viewModel.clearToast()
        }
    }

    // Predictive back handler
    BackHandler(enabled = uiState.currentScreen != AppScreen.MAIN && uiState.currentScreen != AppScreen.SPLASH) {
        viewModel.navigateBack()
    }

    if (uiState.currentScreen == AppScreen.SPLASH) {
        SplashScreen(
            onExploreClick = { viewModel.dismissSplash() }
        )
    } else {
        val title = when (uiState.currentScreen) {
            AppScreen.MAIN -> when (uiState.currentTab) {
                BottomNavTab.HOME -> "Metro Mumbai"
                BottomNavTab.ROUTES -> "Metro Mumbai"
                BottomNavTab.MAP -> "Metro Mumbai"
                BottomNavTab.SAVED -> "Metro Mumbai"
                BottomNavTab.MORE -> "Metro Mumbai"
            }
            AppScreen.STATION_SEARCH -> "Select Station"
            AppScreen.JOURNEY_DETAILS -> "Route Navigation"
            AppScreen.STATION_DETAILS -> "Station Details"
            AppScreen.SERVICE_ALERTS -> "Live Alerts"
            AppScreen.AI_ASSISTANT -> "Transit Assistant"
            AppScreen.SPLASH -> "Metro Mumbai"
        }

        val subtitle = when (uiState.currentScreen) {
            AppScreen.MAIN -> when (uiState.currentTab) {
                BottomNavTab.HOME -> "Home"
                BottomNavTab.ROUTES -> "Routes"
                BottomNavTab.MAP -> "Metro Map"
                BottomNavTab.SAVED -> "Saved"
                BottomNavTab.MORE -> "More"
            }
            AppScreen.STATION_SEARCH -> "Live Trip Navigator"
            AppScreen.JOURNEY_DETAILS -> "Live Trip Navigator"
            AppScreen.STATION_DETAILS -> "Station Details"
            AppScreen.SERVICE_ALERTS -> "Live Trip Navigator"
            AppScreen.AI_ASSISTANT -> "Maps Grounded"
            AppScreen.SPLASH -> "Welcome"
        }

        val showBack = uiState.currentScreen != AppScreen.MAIN

        Scaffold(
            topBar = {
                AppTopBar(
                    title = title,
                    subtitle = subtitle,
                    showBackButton = showBack,
                    onBackClick = { viewModel.navigateBack() },
                    onNotificationsClick = { viewModel.openServiceAlerts() },
                    onProfileClick = { showAiAssistant = true }
                )
            },
            bottomBar = {
                if (uiState.currentScreen == AppScreen.MAIN) {
                    AppBottomNavBar(
                        selectedTab = uiState.currentTab,
                        onTabSelected = { viewModel.navigateToTab(it) }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (uiState.currentScreen) {
                    AppScreen.MAIN -> {
                        when (uiState.currentTab) {
                            BottomNavTab.HOME -> HomeScreen(uiState = uiState, viewModel = viewModel)
                            BottomNavTab.ROUTES -> RoutesScreen(uiState = uiState, viewModel = viewModel)
                            BottomNavTab.MAP -> MetroMapScreen(uiState = uiState, viewModel = viewModel)
                            BottomNavTab.SAVED -> SavedScreen(uiState = uiState, viewModel = viewModel)
                            BottomNavTab.MORE -> MoreScreen(uiState = uiState, viewModel = viewModel)
                        }
                    }
                    AppScreen.STATION_SEARCH -> StationSearchScreen(uiState = uiState, viewModel = viewModel)
                    AppScreen.JOURNEY_DETAILS -> JourneyDetailsScreen(uiState = uiState, viewModel = viewModel)
                    AppScreen.STATION_DETAILS -> StationDetailsScreen(uiState = uiState, viewModel = viewModel)
                    AppScreen.SERVICE_ALERTS -> ServiceAlertsScreen(uiState = uiState, viewModel = viewModel)
                    AppScreen.AI_ASSISTANT -> {
                        showAiAssistant = true
                    }
                    AppScreen.SPLASH -> {}
                }

                // In-app Toast message
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    AppToastNotification(
                        message = uiState.toastMessage,
                        onDismiss = { viewModel.clearToast() }
                    )
                }
            }
        }
    }

    if (showAiAssistant) {
        AiAssistantDialog(
            station = uiState.selectedStationDetails ?: uiState.originStation,
            onDismiss = { showAiAssistant = false }
        )
    }
}
