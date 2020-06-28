/**
 * Copyright (C) 2020 by Qifan YANG (@underwindfall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
