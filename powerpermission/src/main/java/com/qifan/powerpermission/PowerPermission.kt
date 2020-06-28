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
package com.qifan.powerpermission

import com.qifan.powerpermission.core.PowerPermissionManager
import com.qifan.powerpermission.data.Configuration
import com.qifan.powerpermission.data.DefaultConfiguration
import com.qifan.powerpermission.data.PermissionResult
import kotlin.properties.Delegates.notNull

typealias Permission = String
typealias RequestCode = Int
typealias PermissionCallback = (PermissionResult) -> Unit

/**
 * Declaration PowerPermission do initial work
 */
object PowerPermission {
    internal var configuration: Configuration by notNull()

    /**
     * Init PowerPermission to make everything prepare to work.
     * @param configuration global configuration set custom settings in PowerPermission
     */
    fun init(configuration: Configuration = DefaultConfiguration()): PowerPermissionManager {
        this.configuration = configuration
        return PowerPermissionManager()
    }
}
