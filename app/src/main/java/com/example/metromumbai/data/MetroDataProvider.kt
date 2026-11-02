package com.example.metromumbai.data

import com.example.metromumbai.corejava.MetroGraph
import com.example.metromumbai.corejava.MetroStation

data class LineInfo(
    val id: String,
    val name: String,
    val displayName: String,
    val colorHex: String,
    val terminusSummary: String,
    val operationalStationsCount: Int,
    val headwayMinutes: Double,
    val statusText: String
)

data class ServiceAlertItem(
    val id: String,
    val lineId: String,
    val lineTag: String,
    val lineTagColor: String,
    val title: String,
    val statusBadge: String,
    val statusBadgeColor: String,
    val description: String,
    val category: String, // "all", "active", "maintenance", "planned"
    val timestamp: String,
    val metric1Label: String,
    val metric1Value: String,
    val metric2Label: String,
    val metric2Value: String,
    val metric3Label: String,
    val metric3Value: String,
    val subCardTitle: String? = null,
    val subCardDesc: String? = null,
    val subCardIcon: String? = null
)

data class SavedRouteItem(
    val id: String,
    val title: String,
    val originStationId: String,
    val originName: String,
    val destinationStationId: String,
    val destinationName: String,
    val line1: String,
    val line2: String?,
    val durationMinutes: Int,
    val fareInr: Int,
    val interchangeNote: String,
    val isDefault: Boolean = false,
    val tag: String? = null
)

data class StarredStationItem(
    val stationId: String,
    val name: String,
    val lineName: String,
    val lineColorHex: String,
    val nextTrainMinutes: Int
)

data class StationAmenity(
    val iconName: String,
    val title: String,
    val subtitle: String,
    val isAvailable: Boolean = true
)

object MetroDataProvider {

    val lines = listOf(
        LineInfo("LINE_1", "Line 1", "Line 1 (Blue Line)", "#0284C7", "Versova ↔ Ghatkopar", 12, 3.5, "Normal Service"),
        LineInfo("LINE_2A", "Line 2A", "Line 2A (Yellow Line)", "#EAB308", "Dahisar East ↔ D.N. Nagar", 17, 4.0, "Normal Service"),
        LineInfo("LINE_3", "Line 3", "Line 3 (Aqua Line)", "#06B6D4", "Aarey JVLR ↔ BKC (Phase 1)", 10, 6.0, "Extended Hours Active"),
        LineInfo("LINE_7", "Line 7", "Line 7 (Red Line)", "#EF4444", "Dahisar East ↔ Gundavali", 14, 4.0, "Normal Service")
    )

    fun buildMetroGraph(): MetroGraph {
        val graph = MetroGraph()

        // LINE 1 STATIONS (Versova to Ghatkopar)
        val l1Stations = listOf(
            MetroStation("L1_VERSOVA", "Versova", "LINE_1", "Line 1 Blue", "#0284C7", 19.1315, 72.8164, false, listOf("LINE_1"), "Westbound Terminal", 2, "Exit Gate 1 & 2 to J.P. Road", "Zone A"),
            MetroStation("L1_DN_NAGAR", "D.N. Nagar", "LINE_1", "Line 1 Blue", "#0284C7", 19.1256, 72.8276, true, listOf("LINE_1", "LINE_2A"), "Interchange with Line 2A Yellow via Link Rd Concourse", 4, "Exit to Link Road", "Zone A"),
            MetroStation("L1_AZAD_NAGAR", "Azad Nagar", "LINE_1", "Line 1 Blue", "#0284C7", 19.1248, 72.8368, false, listOf("LINE_1"), "", 2, "Exit to Veera Desai Rd", "Zone A"),
            MetroStation("L1_ANDHERI", "Andheri", "LINE_1", "Line 1 Blue", "#0284C7", 19.1197, 72.8464, true, listOf("LINE_1", "RAILWAY_WESTERN"), "Direct skywalk connection to Andheri Suburban Railway Station", 4, "West & East Railway Footbridges", "Zone A"),
            MetroStation("L1_WEH", "Western Express Highway (WEH)", "LINE_1", "Line 1 Blue", "#0284C7", 19.1165, 72.8569, true, listOf("LINE_1", "LINE_7"), "180m covered all-weather Skywalk Bridge to Line 7 Gundavali Station", 4, "Exit Gate 1 & 2 to WEH Flyover", "Zone A"),
            MetroStation("L1_CHAKALA", "Chakala (J.B. Nagar)", "LINE_1", "Line 1 Blue", "#0284C7", 19.1118, 72.8645, false, listOf("LINE_1"), "", 2, "Exit to Kurla Andheri Road", "Zone A"),
            MetroStation("L1_AIRPORT_RD", "Airport Road", "LINE_1", "Line 1 Blue", "#0284C7", 19.1098, 72.8745, false, listOf("LINE_1"), "", 2, "Exit to Marol Industrial Area", "Zone A"),
            MetroStation("L1_MAROL_NAKA", "Marol Naka", "LINE_1", "Line 1 Blue", "#0284C7", 19.1082, 72.8805, true, listOf("LINE_1", "LINE_3"), "Elevated to underground escalator/lift connection to Line 3 Aqua", 4, "Exit Gate 1-4 to Andheri-Ghatkopar Link Rd", "Zone A"),
            MetroStation("L1_SAKI_NAKA", "Saki Naka", "LINE_1", "Line 1 Blue", "#0284C7", 19.1039, 72.8879, false, listOf("LINE_1"), "", 2, "Exit to Saki Naka Junction", "Zone A"),
            MetroStation("L1_ASALPHA", "Asalpha", "LINE_1", "Line 1 Blue", "#0284C7", 19.0987, 72.8945, false, listOf("LINE_1"), "", 2, "Exit to Subhash Nagar", "Zone A"),
            MetroStation("L1_JAGRUTI_NAGAR", "Jagruti Nagar", "LINE_1", "Line 1 Blue", "#0284C7", 19.0934, 72.9015, false, listOf("LINE_1"), "", 2, "Exit to LBS Marg", "Zone A"),
            MetroStation("L1_GHATKOPAR", "Ghatkopar", "LINE_1", "Line 1 Blue", "#0284C7", 19.0864, 72.9081, true, listOf("LINE_1", "RAILWAY_CENTRAL"), "Integrated concourse connection to Ghatkopar Central Railway Station", 2, "Direct FOB to Central Railway Platforms", "Zone A")
        )
        l1Stations.forEach { graph.addStation(it) }

        // Line 1 Edges (11 segments)
        for (i in 0 until l1Stations.size - 1) {
            val a = l1Stations[i].stationId
            val b = l1Stations[i + 1].stationId
            graph.addBidirectionalEdge(a, b, "LINE_1", false, 2.0, 1.0, "Line 1 Blue track")
        }

        // LINE 7 STATIONS (Dahisar East to Gundavali)
        val l7Stations = listOf(
            MetroStation("L7_DAHISAR_E", "Dahisar East", "LINE_7", "Line 7 Red", "#EF4444", 19.2564, 72.8592, true, listOf("LINE_7", "LINE_2A"), "North Suburbs Ring Junction with Line 2A", 4, "Exit to WEH Highway North", "Zone B"),
            MetroStation("L7_OVARIPADA", "Ovaripada", "LINE_7", "Line 7 Red", "#EF4444", 19.2485, 72.8615, false, listOf("LINE_7"), "", 2, "Exit to Dahisar Check Naka", "Zone B"),
            MetroStation("L7_NATIONAL_PARK", "National Park", "LINE_7", "Line 7 Red", "#EF4444", 19.2395, 72.8635, false, listOf("LINE_7"), "Gate to Sanjay Gandhi National Park", 2, "Exit to SGNP Main Gate", "Zone B"),
            MetroStation("L7_DEVIPADA", "Devipada", "LINE_7", "Line 7 Red", "#EF4444", 19.2315, 72.8655, false, listOf("LINE_7"), "", 2, "Exit to Borivali East", "Zone B"),
            MetroStation("L7_MAGATHANE", "Magathane", "LINE_7", "Line 7 Red", "#EF4444", 19.2235, 72.8675, false, listOf("LINE_7"), "", 2, "Exit to Tata Power Hub", "Zone B"),
            MetroStation("L7_POISAR", "Poisar", "LINE_7", "Line 7 Red", "#EF4444", 19.2135, 72.8695, false, listOf("LINE_7"), "", 2, "Exit to Kandivali East", "Zone B"),
            MetroStation("L7_AKURLI", "Akurli", "LINE_7", "Line 7 Red", "#EF4444", 19.2035, 72.8715, false, listOf("LINE_7"), "", 2, "Exit to Growels 101 Mall", "Zone B"),
            MetroStation("L7_KURAR", "Kurar", "LINE_7", "Line 7 Red", "#EF4444", 19.1915, 72.8735, false, listOf("LINE_7"), "", 2, "Exit to Malad East", "Zone B"),
            MetroStation("L7_DINDOSHI", "Dindoshi", "LINE_7", "Line 7 Red", "#EF4444", 19.1805, 72.8755, false, listOf("LINE_7"), "", 2, "Exit to Oberoi Mall Hub", "Zone B"),
            MetroStation("L7_AAREY", "Aarey", "LINE_7", "Line 7 Red", "#EF4444", 19.1665, 72.8785, false, listOf("LINE_7"), "", 2, "Exit to Aarey Colony Road", "Zone A"),
            MetroStation("L7_GOREGAON_E", "Goregaon East", "LINE_7", "Line 7 Red", "#EF4444", 19.1555, 72.8795, false, listOf("LINE_7"), "", 2, "Exit to Nirlon Knowledge Park", "Zone A"),
            MetroStation("L7_JOGESHWARI_E", "Jogeshwari East", "LINE_7", "Line 7 Red", "#EF4444", 19.1405, 72.8815, false, listOf("LINE_7"), "", 2, "Exit to JVLR Junction", "Zone A"),
            MetroStation("L7_MOGRA", "Mogra", "LINE_7", "Line 7 Red", "#EF4444", 19.1275, 72.8825, false, listOf("LINE_7"), "", 2, "Exit to Natwar Nagar", "Zone A"),
            MetroStation("L7_GUNDAVALI", "Gundavali / Andheri E", "LINE_7", "Line 7 Red", "#EF4444", 19.1170, 72.8575, true, listOf("LINE_7", "LINE_1"), "Covered High-Speed Skywalk Bridge to Line 1 WEH Station", 4, "Exit Gate 3 connects to Western Express Highway", "Zone A")
        )
        l7Stations.forEach { graph.addStation(it) }

        for (i in 0 until l7Stations.size - 1) {
            val a = l7Stations[i].stationId
            val b = l7Stations[i + 1].stationId
            graph.addBidirectionalEdge(a, b, "LINE_7", false, 2.5, 1.2, "Line 7 Red track")
        }

        // LINE 2A STATIONS (Dahisar East to D.N. Nagar)
        val l2aStations = listOf(
            MetroStation("L2A_DAHISAR_E", "Dahisar East (Yellow)", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.2564, 72.8592, true, listOf("LINE_2A", "LINE_7"), "Northern Terminal, connects to Line 7 Red", 4, "Exit to Link Rd North", "Zone B"),
            MetroStation("L2A_ANAND_NAGAR", "Anand Nagar", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.2505, 72.8552, false, listOf("LINE_2A"), "", 2, "Exit to Ratan Nagar", "Zone B"),
            MetroStation("L2A_KANDARPADA", "Kandarpada", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.2435, 72.8505, false, listOf("LINE_2A"), "", 2, "Exit to Dahisar Link Rd", "Zone B"),
            MetroStation("L2A_MANDAPESHWAR", "Mandapeshwar", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.2365, 72.8465, false, listOf("LINE_2A"), "", 2, "Exit to IC Colony", "Zone B"),
            MetroStation("L2A_EKSAR", "Eksar", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.2305, 72.8425, false, listOf("LINE_2A"), "", 2, "Exit to Borivali Link Rd", "Zone B"),
            MetroStation("L2A_BORIVALI_W", "Borivali West", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.2245, 72.8385, false, listOf("LINE_2A"), "", 2, "Exit to Don Bosco Rd", "Zone B"),
            MetroStation("L2A_PAHADI_EKSAR", "Pahadi Eksar", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.2185, 72.8345, false, listOf("LINE_2A"), "", 2, "Exit to Shimpoli", "Zone B"),
            MetroStation("L2A_KANDIVALI_W", "Kandivali West", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.2105, 72.8315, false, listOf("LINE_2A"), "", 2, "Exit to MG Road", "Zone B"),
            MetroStation("L2A_DAHANUKARWADI", "Dahanukarwadi", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.2045, 72.8285, false, listOf("LINE_2A"), "", 2, "Exit to Mahavir Nagar", "Zone B"),
            MetroStation("L2A_VALNAI", "Valnai", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.1965, 72.8275, false, listOf("LINE_2A"), "", 2, "Exit to Orlem Malad", "Zone B"),
            MetroStation("L2A_MALAD_W", "Malad West", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.1885, 72.8265, false, listOf("LINE_2A"), "", 2, "Exit to Infiniti Mall Link Rd", "Zone B"),
            MetroStation("L2A_LOWER_MALAD", "Lower Malad", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.1805, 72.8260, false, listOf("LINE_2A"), "", 2, "Exit to Inorbit Mall", "Zone B"),
            MetroStation("L2A_PAHADI_GOREGAON", "Pahadi Goregaon", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.1685, 72.8265, false, listOf("LINE_2A"), "", 2, "Exit to Bangur Nagar", "Zone A"),
            MetroStation("L2A_GOREGAON_W", "Goregaon West", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.1605, 72.8275, false, listOf("LINE_2A"), "", 2, "Exit to SV Road Connector", "Zone A"),
            MetroStation("L2A_OSHIWARA", "Oshiwara", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.1515, 72.8285, false, listOf("LINE_2A"), "", 2, "Exit to Mega Mall", "Zone A"),
            MetroStation("L2A_LOWER_OSHIWARA", "Lower Oshiwara", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.1415, 72.8290, false, listOf("LINE_2A"), "", 2, "Exit to Lokhandwala Complex", "Zone A"),
            MetroStation("L2A_DN_NAGAR", "D.N. Nagar (Yellow)", "LINE_2A", "Line 2A Yellow", "#EAB308", 19.1256, 72.8276, true, listOf("LINE_2A", "LINE_1"), "Major Interchange with Line 1 Blue", 4, "Direct concourse walkway to Line 1", "Zone A")
        )
        l2aStations.forEach { graph.addStation(it) }

        for (i in 0 until l2aStations.size - 1) {
            val a = l2aStations[i].stationId
            val b = l2aStations[i + 1].stationId
            graph.addBidirectionalEdge(a, b, "LINE_2A", false, 2.2, 1.1, "Line 2A Yellow track")
        }

        // LINE 3 (AQUA) STATIONS (Phase 1 Active: Aarey JVLR to BKC)
        val l3Stations = listOf(
            MetroStation("L3_AAREY_JVLR", "Aarey JVLR", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.1425, 72.8875, false, listOf("LINE_3"), "Phase 1 Underground Terminus", 2, "Exit to JVLR East", "Zone A"),
            MetroStation("L3_SEEPZ", "SEEPZ Village", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.1295, 72.8825, false, listOf("LINE_3"), "Underground SEEPZ Gateway", 2, "Exit to SEEPZ Gate 1", "Zone A"),
            MetroStation("L3_MIDC", "MIDC Andheri", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.1185, 72.8795, false, listOf("LINE_3"), "Underground MIDC Concourse", 2, "Exit to Marol Industrial Area", "Zone A"),
            MetroStation("L3_MAROL_NAKA", "Marol Naka (Aqua)", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.1082, 72.8805, true, listOf("LINE_3", "LINE_1"), "Deep underground station connected to Line 1 Blue via escalators", 4, "Direct Concourse Sub-surface Pass", "Zone A"),
            MetroStation("L3_CSMIA_T2", "CSMIA T2 (Airport Terminal)", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.0985, 72.8715, true, listOf("LINE_3"), "Direct Aeromall Connection & Check-in luggage points", 2, "Direct Aeromall & Terminal 2 arrivals/departures", "Zone A"),
            MetroStation("L3_SAHAR_RD", "Sahar Road", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.0905, 72.8635, false, listOf("LINE_3"), "", 2, "Exit to Sahar Cargo Terminal", "Zone A"),
            MetroStation("L3_CSMIA_T1", "CSMIA T1 (Domestic)", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.0835, 72.8555, false, listOf("LINE_3"), "Direct walk to Domestic Airport departures", 2, "Exit to Terminal 1 Domestic Gate", "Zone A"),
            MetroStation("L3_SANTACRUZ", "Santacruz Metro", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.0745, 72.8465, false, listOf("LINE_3"), "", 2, "Exit to Vakola Junction", "Zone A"),
            MetroStation("L3_BANDRA_COLONY", "Bandra Colony", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.0665, 72.8495, false, listOf("LINE_3"), "", 2, "Exit to Kalanagar", "Zone A"),
            MetroStation("L3_BKC", "BKC (Bandra Kurla)", "LINE_3", "Line 3 Aqua", "#06B6D4", 19.0595, 72.8655, false, listOf("LINE_3"), "Underground BKC Terminal Concourse", 4, "Exit to Jio World Centre & MCA", "Zone A")
        )
        l3Stations.forEach { graph.addStation(it) }

        for (i in 0 until l3Stations.size - 1) {
            val a = l3Stations[i].stationId
            val b = l3Stations[i + 1].stationId
            graph.addBidirectionalEdge(a, b, "LINE_3", false, 2.3, 1.3, "Line 3 Aqua underground tunnel")
        }

        // VERIFIED INTERCHANGE EDGES
        // 1. WEH (Line 1) <-> Gundavali (Line 7) via 180m elevated Skywalk (3 mins walk)
        graph.addBidirectionalEdge(
            "L1_WEH", "L7_GUNDAVALI", "TRANSFER_WEH_GUNDAVALI",
            true, 3.0, 0.18,
            "180m sheltered high-speed Skywalk bridge with moving escalators & elevators"
        )

        // 2. D.N. Nagar (Line 1) <-> D.N. Nagar (Line 2A) via Concourse (2 mins walk)
        graph.addBidirectionalEdge(
            "L1_DN_NAGAR", "L2A_DN_NAGAR", "TRANSFER_DN_NAGAR",
            true, 2.0, 0.10,
            "Direct covered interchange concourse between Line 1 and Line 2A"
        )

        // 3. Dahisar East (Line 2A) <-> Dahisar East (Line 7) (2 mins walk)
        graph.addBidirectionalEdge(
            "L2A_DAHISAR_E", "L7_DAHISAR_E", "TRANSFER_DAHISAR_E",
            true, 2.0, 0.10,
            "Integrated platform concourse transfer between Yellow Line 2A and Red Line 7"
        )

        // 4. Marol Naka (Line 1) <-> Marol Naka (Line 3 Aqua) (3 mins walk)
        graph.addBidirectionalEdge(
            "L1_MAROL_NAKA", "L3_MAROL_NAKA", "TRANSFER_MAROL_NAKA",
            true, 3.0, 0.15,
            "Integrated vertical escalator & elevator transit between elevated Line 1 and underground Line 3"
        )

        return graph
    }

    val defaultSavedRoutes = listOf(
        SavedRouteItem(
            id = "saved_1",
            title = "Work Commute",
            originStationId = "L1_VERSOVA",
            originName = "Versova",
            destinationStationId = "L7_GUNDAVALI",
            destinationName = "Gundavali / Andheri E",
            line1 = "Line 1",
            line2 = "Line 7",
            durationMinutes = 28,
            fareInr = 30,
            interchangeNote = "Transfer at Gundavali",
            isDefault = true,
            tag = "Default"
        ),
        SavedRouteItem(
            id = "saved_2",
            title = "Airport Express",
            originStationId = "L1_GHATKOPAR",
            originName = "Ghatkopar",
            destinationStationId = "L3_CSMIA_T2",
            destinationName = "CSMIA Terminal 2",
            line1 = "Line 1",
            line2 = "Line 3 Aqua",
            durationMinutes = 16,
            fareInr = 20,
            interchangeNote = "Direct underground at Marol Naka",
            isDefault = false,
            tag = "Luggage friendly"
        ),
        SavedRouteItem(
            id = "saved_3",
            title = "Weekend Travel",
            originStationId = "L2A_DN_NAGAR",
            originName = "DN Nagar",
            destinationStationId = "L2A_DAHISAR_E",
            destinationName = "Dahisar East",
            line1 = "Line 2A Yellow",
            line2 = null,
            durationMinutes = 36,
            fareInr = 50,
            interchangeNote = "17 stations direct",
            isDefault = false,
            tag = "No interchanges"
        )
    )

    val defaultStarredStations = listOf(
        StarredStationItem("L1_GHATKOPAR", "Ghatkopar", "Line 1 Blue", "#0284C7", 2),
        StarredStationItem("L3_BKC", "BKC", "Line 3 Aqua", "#06B6D4", 5),
        StarredStationItem("L1_ANDHERI", "Andheri", "Line 1 Blue", "#0284C7", 3),
        StarredStationItem("L1_WEH", "WEH / Gundavali", "Line 1 & 7", "#EF4444", 2)
    )

    val serviceAlerts = listOf(
        ServiceAlertItem(
            id = "alert_1",
            lineId = "LINE_1",
            lineTag = "Line 1 Blue",
            lineTagColor = "#0284C7",
            title = "Versova ↔ Ghatkopar",
            statusBadge = "Normal Service",
            statusBadgeColor = "#10B981",
            description = "Fleet running standard peak frequency of 3.5 minutes. All 12 stations fully open and operational with 100% on-time performance index.",
            category = "all",
            timestamp = "Updated 2m ago",
            metric1Label = "Headway",
            metric1Value = "3.5 min",
            metric2Label = "Performance",
            metric2Value = "100%",
            metric3Label = "Stations",
            metric3Value = "12 Open"
        ),
        ServiceAlertItem(
            id = "alert_2",
            lineId = "LINE_3",
            lineTag = "Line 3 Aqua",
            lineTagColor = "#06B6D4",
            title = "Aarey JVLR ↔ BKC",
            statusBadge = "New Operating Hours",
            statusBadgeColor = "#0284C7",
            description = "Phase 1 underground service timings extended up to 22:30 IST on weekdays. Additional airport shuttle feeder services active at CSMIA Terminal 1 & Terminal 2 stations.",
            category = "active",
            timestamp = "Today, 09:30 AM",
            metric1Label = "Headway",
            metric1Value = "6.5 min",
            metric2Label = "Extended",
            metric2Value = "22:30 IST",
            metric3Label = "Underground",
            metric3Value = "10 Stns",
            subCardTitle = "Airport T1 & T2 Direct Interchange",
            subCardDesc = "Connecting aerobridge walkways clear & staffed 24/7",
            subCardIcon = "flight"
        ),
        ServiceAlertItem(
            id = "alert_3",
            lineId = "LINE_7",
            lineTag = "Line 7 Red & 2A",
            lineTagColor = "#EF4444",
            title = "Aarey Station Concourse",
            statusBadge = "Facility Notice",
            statusBadgeColor = "#F59E0B",
            description = "Scheduled lift maintenance at Aarey Station Concourse Gate 2 between 11:00 AM – 14:00 PM. Alternate step-free ramp and lift operational at Gate 1.",
            category = "maintenance active",
            timestamp = "Today, 08:45 AM",
            metric1Label = "Lift Status",
            metric1Value = "Gate 2 Serv",
            metric2Label = "Gate 1",
            metric2Value = "Active",
            metric3Label = "Duration",
            metric3Value = "3 Hours",
            subCardTitle = "Mobility Assistance Active",
            subCardDesc = "Dedicated station staff available at Gate 1 ramp entrance",
            subCardIcon = "accessible"
        ),
        ServiceAlertItem(
            id = "alert_4",
            lineId = "LINE_2A",
            lineTag = "Line 2A Yellow",
            lineTagColor = "#EAB308",
            title = "Dahisar ↔ DN Nagar",
            statusBadge = "Sunday Night Work",
            statusBadgeColor = "#565E74",
            description = "Preventative overhead catenary inspection scheduled this Sunday between 00:30 – 04:30 IST. Regular daytime passenger operations remain entirely unaffected.",
            category = "planned",
            timestamp = "Yesterday",
            metric1Label = "Window",
            metric1Value = "00:30 - 04:30",
            metric2Label = "Impact",
            metric2Value = "Zero Day",
            metric3Label = "Daytime",
            metric3Value = "Regular"
        )
    )

    val stationAmenitiesList = listOf(
        StationAmenity("elevator", "Elevators & Lifts", "Concourse to Platform • All gates"),
        StationAmenity("accessible_forward", "Wheelchair Accessible", "Step-free ramps & tactile flooring"),
        StationAmenity("local_parking", "Dedicated Parking", "Two-wheeler & Car at Gate 1"),
        StationAmenity("wc", "Sanitized Restrooms", "Clean washrooms on Concourse Level"),
        StationAmenity("local_atm", "ATM & Drinking Water", "RO Purified Water • SBI/HDFC ATMs"),
        StationAmenity("health_and_safety", "First Aid & Security", "CISF / Metro Police booth 24x7")
    )
}
