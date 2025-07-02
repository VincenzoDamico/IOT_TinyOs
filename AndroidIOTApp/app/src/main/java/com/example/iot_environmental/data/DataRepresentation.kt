// com.example.iot_environmental.data.SensorData.kt
package com.example.iot_environmental.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties // Useful to ignore fields that might be in Firebase but not in your data class
data class SensorReading(
    val humidity: Double = 0.0,
    val luminosity: Int = 0,
    val temperature: Double = 0.0
)

// Data representing a node's latest readings
data class NodeData(
    val nodeId: String,
    val timestamp: String, // Keep as String for direct display, or convert to Long/Instant for sorting
    val reading: SensorReading
)

// Data representing a base station with its nodes
data class BaseStationData(
    val baseStationId: String,
    val nodes: List<NodeData>
)