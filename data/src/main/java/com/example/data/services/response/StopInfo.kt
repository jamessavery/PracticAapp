package com.example.data.services.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StopInfo(
    @JsonProperty("\$type") var type: String? = null,
    @JsonProperty("naptanId") var naptanId: String? = null,
    @JsonProperty("modes") var modes: ArrayList<String> = arrayListOf(),
    @JsonProperty("icsCode") var icsCode: String? = null,
    @JsonProperty("stopType") var stopType: String? = null,
    @JsonProperty("stationNaptan") var stationNaptan: String? = null,
    @JsonProperty("lines") var lines: ArrayList<Lines> = arrayListOf(),
    @JsonProperty("lineGroup") var lineGroup: ArrayList<LineGroup> = arrayListOf(),
    @JsonProperty("lineModeGroups") var lineModeGroups: ArrayList<LineModeGroups> = arrayListOf(),
    @JsonProperty("status") var status: Boolean? = null,
    @JsonProperty("id") var id: String? = null,
    @JsonProperty("commonName") var commonName: String? = null,
    @JsonProperty("placeType") var placeType: String? = null,
    @JsonProperty("additionalProperties") var additionalProperties: ArrayList<String> = arrayListOf(),
    @JsonProperty("children") var children: ArrayList<Children> = arrayListOf(),
    @JsonProperty("lat") var lat: Double? = null,
    @JsonProperty("lon") var lon: Double? = null

)

@JsonClass(generateAdapter = true)
data class LineModeGroups(

    @JsonProperty("\$type") var type: String? = null,
    @JsonProperty("modeName") var modeName: String? = null,
    @JsonProperty("lineIdentifier") var lineIdentifier: ArrayList<String> = arrayListOf()

)

@JsonClass(generateAdapter = true)
data class AdditionalProperty(
    @JsonProperty("\$type")
    val type: String,

    val category: String,
    val key: String,
    val sourceSystemKey: String,
    val value: String
)

@JsonClass(generateAdapter = true)
data class LineGroup(
    @JsonProperty("\$type")
    val type: String,
    @JsonProperty("naptanIdReference")
    val naptanIDReference: String,
    val stationAtcoCode: String,
    val lineIdentifier: List<String>
)

@JsonClass(generateAdapter = true)
data class LineModeGroup(
    @JsonProperty("\$type")
    val type: String,
    val modeName: String,
    val lineIdentifier: List<String>
)

@JsonClass(generateAdapter = true)
data class Children(
    @JsonProperty("\$type") var type: String? = null,
    @JsonProperty("naptanId") var naptanId: String? = null,
    @JsonProperty("indicator") var indicator: String? = null,
    @JsonProperty("stopLetter") var stopLetter: String? = null,
    @JsonProperty("modes") var modes: ArrayList<String> = arrayListOf(),
    @JsonProperty("icsCode") var icsCode: String? = null,
    @JsonProperty("stopType") var stopType: String? = null,
    @JsonProperty("stationNaptan") var stationNaptan: String? = null,
    @JsonProperty("lines") var lines: ArrayList<Lines> = arrayListOf(),
    @JsonProperty("lineGroup") var lineGroup: ArrayList<LineGroup> = arrayListOf(),
    @JsonProperty("lineModeGroups") var lineModeGroups: ArrayList<LineModeGroups> = arrayListOf(),
    @JsonProperty("status") var status: Boolean? = null,
    @JsonProperty("id") var id: String? = null,
    @JsonProperty("commonName") var commonName: String? = null,
    @JsonProperty("placeType") var placeType: String? = null,
    @JsonProperty("additionalProperties") var additionalProperties: ArrayList<AdditionalProperties> = arrayListOf(),
    @JsonProperty("children") var children: ArrayList<String> = arrayListOf(),
    @JsonProperty("lat") var lat: Double? = null,
    @JsonProperty("lon") var lon: Double? = null

)

@JsonClass(generateAdapter = true)
data class AdditionalProperties(
    @JsonProperty("\$type") var type: String? = null,
    @JsonProperty("category") var category: String? = null,
    @JsonProperty("key") var key: String? = null,
    @JsonProperty("sourceSystemKey") var sourceSystemKey: String? = null,
    @JsonProperty("value") var value: String? = null

)

@JsonClass(generateAdapter = true)
data class Lines(

    @JsonProperty("\$type") var type: String? = null,
    @JsonProperty("id") var id: String? = null,
    @JsonProperty("name") var name: String? = null,
    @JsonProperty("uri") var uri: String? = null,
    @JsonProperty("crowding") var crowding: Crowding? = Crowding(),
    @JsonProperty("routeType") var routeType: String? = null,
    @JsonProperty("status") var status: String? = null

)

@JsonClass(generateAdapter = true)
data class Line(
    @JsonProperty("\$type")
    val type: String,
    val id: String,
    val name: String,
    val uri: String,
    @JsonProperty("\$type")
    val lineType: String,
    val crowding: Crowding,
    val routeType: String,
    val status: String
)

@JsonClass(generateAdapter = true)
data class Crowding(
    @JsonProperty("\$type")
    val type: String? = null
)

enum class Type(val value: String) {
    Line("Line");

    companion object {
        public fun fromValue(value: String): Type = when (value) {
            "Line" -> Line
            else -> throw IllegalArgumentException()
        }
    }
}

enum class RouteType(val value: String) {
    Unknown("Unknown");

    companion object {
        public fun fromValue(value: String): RouteType = when (value) {
            "Unknown" -> Unknown
            else -> throw IllegalArgumentException()
        }
    }
}

//data class StopInfo (
//    val id: String,
//    val operationType: Int,
//    val vehicleID: String,
//    val naptanID: String,
//    val stationName: String,
//    val lineID: String,
//    val lineName: String,
//    val platformName: String,
//    val direction: String,
//    val String: String,
//    val destinationNaptanID: String,
//    val destinationName: String,
//    val timestamp: String,
//    val timeToStation: Int,
//    val currentLocation: String,
//    val towards: String,
//    val expectedArrival: String,
//    val timeToLive: String,
//    val modeName: String,
//    val timing: PredictionTiming
//)
//
//data class PredictionTiming (
//    val countdownServerAdjustment: String,
//    val source: String,
//    val insert: String,
//    val read: String,
//    val sent: String,
//    val received: String
//)