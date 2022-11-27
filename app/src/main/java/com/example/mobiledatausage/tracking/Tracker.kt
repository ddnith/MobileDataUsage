package com.example.mobiledatausage.tracking

import android.util.Log
import com.example.mobiledatausage.model.Record

object Tracker {
    fun onPageSeenEvent(pair: Pair<String, List<Record>>) {
        Log.d("Tracker", "User is watching record for year ${pair.first}")
    }

    fun onRotationEvent() {
        Log.d("Tracker", "Device Rotated")
    }
}