package com.example.iot_environmental.ui.view.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.iot_environmental.data.BaseStationData
import com.example.iot_environmental.data.NodeData
import com.example.iot_environmental.data.SensorReading


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException // Import this for robust parsing

class FirebaseViewModel : ViewModel() {

    private val _allBaseStationsData = MutableLiveData<List<BaseStationData>>()
    val allBaseStationsData: LiveData<List<BaseStationData>> = _allBaseStationsData

    private val database = FirebaseDatabase.getInstance()
    private val databaseRef = database.getReference("baseStation") // Assuming your root is 'baseStations'

    init {
        // Fetch data when the ViewModel is initialized
        fetchRealtimeData()
    }

    private fun fetchRealtimeData() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                val baseStationList = mutableListOf<BaseStationData>()

                snapshot.children.forEach { baseStationSnapshot ->
                    val baseStationId = baseStationSnapshot.key ?: return@forEach // e.g., "1"
                    val nodesInBaseStation = mutableListOf<NodeData>()

                    baseStationSnapshot.children.forEach { nodeSnapshot ->
                        val nodeId = nodeSnapshot.key ?: return@forEach // e.g., "1", "2"
                        var latestTimestamp: LocalDateTime? = null
                        var latestReading: SensorReading? = null
                        var latestTimestampString: String = ""

                        // Iterate through timestamps to find the latest
                        nodeSnapshot.children.forEach { timestampSnapshot ->
                            val timestampStr = timestampSnapshot.key
                            val currentReading = timestampSnapshot.getValue(SensorReading::class.java)

                            if (timestampStr != null && currentReading != null) {
                                try {
                                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")
                                    val currentTimestamp = LocalDateTime.parse(timestampStr, formatter)

                                    if (latestTimestamp == null || currentTimestamp.isAfter(latestTimestamp)) {
                                        latestTimestamp = currentTimestamp
                                        latestReading = currentReading
                                        latestTimestampString = timestampStr
                                    }
                                } catch (e: DateTimeParseException) {
                                    // Handle cases where timestamp string format is unexpected
                                    // Log the error or simply skip this entry
                                    e.printStackTrace()
                                }
                            }
                        }

                        if (latestReading != null) {
                            nodesInBaseStation.add(
                                NodeData(
                                    nodeId = nodeId,
                                    timestamp = latestTimestampString.replace("_", " "),
                                    reading = latestReading!!
                                )
                            )
                        }
                    }
                    baseStationList.add(BaseStationData(baseStationId, nodesInBaseStation))
                }
                _allBaseStationsData.value = baseStationList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                // Log the error, show a message to the user, etc.
                println("Firebase Error: ${error.message}")
            }
        })
    }
}