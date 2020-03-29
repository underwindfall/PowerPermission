package com.qifan.powerpermission.internal.extension

import android.util.Log
import com.qifan.powerpermission.BuildConfig

/**
 * Helper function to do the log debug work
 * @param message basic message
 * @param args additional message
 */
internal fun Any.debug(
    message: String,
    vararg args: Any?
) {
    if (BuildConfig.DEBUG) {
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
    if (BuildConfig.DEBUG) {
        try {
            Log.w(this::class.java.simpleName, message.format(*args))
        } catch (_: Exception) {
        }
    }
}
