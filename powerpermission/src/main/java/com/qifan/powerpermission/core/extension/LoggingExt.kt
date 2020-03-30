package com.qifan.powerpermission.core.extension

import android.util.Log
import com.qifan.powerpermission.PowerPermission

/**
 * Helper function to do the log debug work
 * @param message basic message
 * @param args additional message
 */
internal fun Any.debug(
    message: String,
    vararg args: Any?
) {
    if (PowerPermission.configuration.enableLog) {
        try {
            Log.d(this::class.java.simpleName, message.format(*args))
        } catch (_: Exception) {
        }
    }
}

/**
 * Helper function to do the log warn work
 * @param message basic message
 * @param args additional message
 */
internal fun Any.warn(
    message: String,
    vararg args: Any?
) {
    if (PowerPermission.configuration.enableLog) {
        try {
            Log.w(this::class.java.simpleName, message.format(*args))
        } catch (_: Exception) {
        }
    }
}
