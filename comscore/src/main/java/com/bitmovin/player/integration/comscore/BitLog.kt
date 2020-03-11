package com.bitmovin.player.integration.comscore

import android.util.Log

object BitLog {
    private val TAG = BitLog::class.java.simpleName
    var isEnabled: Boolean = false

    private val stackTraceInfo: String
        get() {
            val trace = Exception().stackTrace
            var info = ""
            // Trace index is 2 levels up
            val traceIndex = 2
            if (trace.size > traceIndex) {
                val classPath = trace[traceIndex].className
                val startIndex = classPath.lastIndexOf(".")
                // Get class name from full path
                var className = classPath.substring(startIndex + 1).trim { char -> char <= ' ' }
                // Remove appended anonymous class name (e.g. $2) if present
                if (className.contains("$")) {
                    className = className.substring(0, className.indexOf("$"))
                }
                info = "[" + className + ":" + trace[traceIndex].methodName + ":" + trace[traceIndex].lineNumber + "]"
            }
            return info
        }

    fun d(message: String) {
        if (isEnabled) {
            Log.d(TAG, "$stackTraceInfo -> $message")
        }
    }

    fun e(message: String) {
        if (isEnabled) {
            Log.e(TAG, "$stackTraceInfo -> $message")
        }
    }

    fun i(message: String) {
        if (isEnabled) {
            Log.i(TAG, "$stackTraceInfo -> $message")
        }
    }

    fun wtf(message: String) {
        if (isEnabled) {
            Log.wtf(TAG, "$stackTraceInfo -> $message")
        }
    }

    fun v(message: String) {
        if (isEnabled) {
            Log.v(TAG, "$stackTraceInfo -> $message")
        }
    }
}
