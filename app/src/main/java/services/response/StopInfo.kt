package services.response

data class StopInfo (
    val id: String,
    val operationType: Int,
    val vehicleID: String,
    val naptanID: String,
    val stationName: String,
    val lineID: String,
    val lineName: String,
    val platformName: String,
    val direction: String,
    val String: String,
    val destinationNaptanID: String,
    val destinationName: String,
    val timestamp: String,
    val timeToStation: Int,
    val currentLocation: String,
    val towards: String,
    val expectedArrival: String,
    val timeToLive: String,
    val modeName: String,
    val timing: PredictionTiming
)

data class PredictionTiming (
    val countdownServerAdjustment: String,
    val source: String,
    val insert: String,
    val read: String,
    val sent: String,
    val received: String
)