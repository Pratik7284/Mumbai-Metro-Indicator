package com.example.metromumbai.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metromumbai.corejava.MetroStation
import com.example.metromumbai.corejava.RouteResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class BottomNavTab {
    HOME,
    ROUTES,
    MAP,
    SAVED,
    MORE
}

enum class AppScreen {
    SPLASH,
    MAIN,
    STATION_SEARCH,
    JOURNEY_DETAILS,
    STATION_DETAILS,
    SERVICE_ALERTS,
    AI_ASSISTANT
}

enum class StationSelectRole {
    ORIGIN,
    DESTINATION,
    INSPECT
}

data class MetroUiState(
    val currentScreen: AppScreen = AppScreen.SPLASH,
    val currentTab: BottomNavTab = BottomNavTab.HOME,
    val originStationId: String = "L1_VERSOVA",
    val destinationStationId: String = "L7_GUNDAVALI",
    val originStation: MetroStation? = null,
    val destinationStation: MetroStation? = null,
    val selectedStationDetails: MetroStation? = null,
    val searchRole: StationSelectRole = StationSelectRole.ORIGIN,
    val searchQuery: String = "",
    val activeLineFilter: String = "all",
    val activeMapLineFilter: String = "all",
    val selectedMapStation: MetroStation? = null,
    val routePreference: String = "fastest", // "fastest", "interchanges", "fare"
    val recommendedRoute: RouteResult? = null,
    val alternativeRoute: RouteResult? = null,
    val savedRoutes: List<SavedRouteItem> = MetroDataProvider.defaultSavedRoutes,
    val starredStationIds: Set<String> = setOf("L1_GHATKOPAR", "L3_BKC", "L1_ANDHERI", "L1_WEH"),
    val recentSearches: List<String> = listOf("Andheri", "Gundavali", "BKC (Bandra Kurla Complex)"),
    val ncmcBalance: Double = 240.0,
    val selectedLanguage: String = "English",
    val isOfflineMode: Boolean = true,
    val toastMessage: String? = null
)

class MetroViewModel : ViewModel() {
    private val repository = MetroRepository()

    private val _uiState = MutableStateFlow(MetroUiState())
    val uiState: StateFlow<MetroUiState> = _uiState.asStateFlow()

    init {
        val origin = repository.getStation(_uiState.value.originStationId)
        val dest = repository.getStation(_uiState.value.destinationStationId)
        val mapInitial = repository.getStation("L1_MAROL_NAKA")
        val weh = repository.getStation("L1_WEH")

        _uiState.update {
            it.copy(
                originStation = origin,
                destinationStation = dest,
                selectedMapStation = mapInitial,
                selectedStationDetails = weh
            )
        }
        recalculateRoutes()
    }

    fun dismissSplash() {
        _uiState.update { it.copy(currentScreen = AppScreen.MAIN, currentTab = BottomNavTab.HOME) }
    }

    fun navigateToTab(tab: BottomNavTab) {
        _uiState.update {
            it.copy(currentScreen = AppScreen.MAIN, currentTab = tab)
        }
    }

    fun openStationSearch(role: StationSelectRole) {
        _uiState.update {
            it.copy(
                currentScreen = AppScreen.STATION_SEARCH,
                searchRole = role,
                searchQuery = "",
                activeLineFilter = "all"
            )
        }
    }

    fun openJourneyDetails() {
        _uiState.update { it.copy(currentScreen = AppScreen.JOURNEY_DETAILS) }
    }

    fun openStationDetails(station: MetroStation) {
        _uiState.update {
            it.copy(
                selectedStationDetails = station,
                currentScreen = AppScreen.STATION_DETAILS
            )
        }
    }

    fun openServiceAlerts() {
        _uiState.update { it.copy(currentScreen = AppScreen.SERVICE_ALERTS) }
    }

    fun openAiAssistant() {
        _uiState.update { it.copy(currentScreen = AppScreen.AI_ASSISTANT) }
    }

    fun navigateBack(): Boolean {
        val current = _uiState.value.currentScreen
        if (current != AppScreen.MAIN && current != AppScreen.SPLASH) {
            _uiState.update { it.copy(currentScreen = AppScreen.MAIN) }
            return true
        }
        return false
    }

    fun swapStations() {
        val currentOriginId = _uiState.value.originStationId
        val currentDestId = _uiState.value.destinationStationId

        val newOrigin = repository.getStation(currentDestId)
        val newDest = repository.getStation(currentOriginId)

        _uiState.update {
            it.copy(
                originStationId = currentDestId,
                destinationStationId = currentOriginId,
                originStation = newOrigin,
                destinationStation = newDest
            )
        }
        recalculateRoutes()
        showToast("Swapped origin and destination")
    }

    fun selectStationForRole(station: MetroStation) {
        when (_uiState.value.searchRole) {
            StationSelectRole.ORIGIN -> {
                _uiState.update {
                    it.copy(
                        originStationId = station.stationId,
                        originStation = station,
                        currentScreen = AppScreen.MAIN
                    )
                }
                addRecentSearch(station.name)
                recalculateRoutes()
            }
            StationSelectRole.DESTINATION -> {
                _uiState.update {
                    it.copy(
                        destinationStationId = station.stationId,
                        destinationStation = station,
                        currentScreen = AppScreen.MAIN
                    )
                }
                addRecentSearch(station.name)
                recalculateRoutes()
            }
            StationSelectRole.INSPECT -> {
                openStationDetails(station)
            }
        }
    }

    fun setRoutePreference(pref: String) {
        _uiState.update { it.copy(routePreference = pref) }
        recalculateRoutes()
    }

    fun recalculateRoutes() {
        val originId = _uiState.value.originStationId
        val destId = _uiState.value.destinationStationId

        val fastest = repository.calculateFastestRoute(originId, destId)
        val bfsAlternative = repository.calculateBfsRoute(originId, destId)

        _uiState.update {
            it.copy(
                recommendedRoute = fastest,
                alternativeRoute = bfsAlternative
            )
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setLineFilter(filter: String) {
        _uiState.update { it.copy(activeLineFilter = filter) }
    }

    fun setMapLineFilter(filter: String) {
        _uiState.update { it.copy(activeMapLineFilter = filter) }
    }

    fun selectMapStation(station: MetroStation) {
        _uiState.update { it.copy(selectedMapStation = station) }
    }

    fun toggleStarred(stationId: String) {
        _uiState.update { state ->
            val set = state.starredStationIds.toMutableSet()
            val isNowStarred = if (set.contains(stationId)) {
                set.remove(stationId)
                false
            } else {
                set.add(stationId)
                true
            }
            val msg = if (isNowStarred) "Station saved to favourites!" else "Station removed from favourites"
            state.copy(starredStationIds = set, toastMessage = msg)
        }
    }

    fun addRecentSearch(query: String) {
        _uiState.update { state ->
            val list = state.recentSearches.toMutableList()
            list.remove(query)
            list.add(0, query)
            state.copy(recentSearches = list.take(10))
        }
    }

    fun removeRecentSearch(query: String) {
        _uiState.update { state ->
            val list = state.recentSearches.toMutableList()
            list.remove(query)
            state.copy(recentSearches = list)
        }
    }

    fun clearAllRecentSearches() {
        _uiState.update { it.copy(recentSearches = emptyList()) }
        showToast("Search history cleared")
    }

    fun topUpNcmcCard(amount: Double) {
        _uiState.update {
            it.copy(
                ncmcBalance = it.ncmcBalance + amount,
                toastMessage = "Added ₹${amount.toInt()} to Mumbai 1 NCMC Card! New Balance: ₹${(it.ncmcBalance + amount).toInt()}"
            )
        }
    }

    fun cycleLanguage() {
        val langs = listOf("English", "मराठी (Marathi)", "हिंदी (Hindi)")
        val currentIdx = langs.indexOf(_uiState.value.selectedLanguage)
        val nextLang = langs[(currentIdx + 1) % langs.size]
        _uiState.update { it.copy(selectedLanguage = nextLang) }
        showToast("Language changed to $nextLang")
    }

    fun toggleOfflineMode() {
        _uiState.update {
            val toggled = !it.isOfflineMode
            val msg = if (toggled) "Offline timetable sync active" else "Live server network sync enabled"
            it.copy(isOfflineMode = toggled, toastMessage = msg)
        }
    }

    fun showToast(msg: String) {
        _uiState.update { it.copy(toastMessage = msg) }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    fun getFilteredStations(): List<MetroStation> {
        val query = _uiState.value.searchQuery.trim().lowercase()
        val filter = _uiState.value.activeLineFilter
        val all = repository.getAllStations()

        return all.filter { st ->
            val matchesQuery = query.isEmpty() ||
                    st.name.lowercase().contains(query) ||
                    st.stationId.lowercase().contains(query) ||
                    st.lineDisplayName.lowercase().contains(query)

            val matchesFilter = when (filter) {
                "all" -> true
                "line1" -> st.lineId == "LINE_1"
                "line2a" -> st.lineId == "LINE_2A"
                "line3" -> st.lineId == "LINE_3"
                "line7" -> st.lineId == "LINE_7"
                "interchange" -> st.isInterchange
                else -> true
            }
            matchesQuery && matchesFilter
        }
    }

    fun getStationById(id: String): MetroStation? = repository.getStation(id)
}
