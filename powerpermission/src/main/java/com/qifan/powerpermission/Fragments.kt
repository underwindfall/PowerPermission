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

import androidx.fragment.app.Fragment
import com.qifan.powerpermission.core.PERMISSION_REQUEST_CODE
import com.qifan.powerpermission.data.Configuration
import com.qifan.powerpermission.data.DefaultConfiguration
import com.qifan.powerpermission.data.hasAllGranted
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

/**
 * extension to simple usage about requesting permissions
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 * @param callback to return after execute requesting permissions
 */
fun Fragment.askPermissions(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null,
    callback: PermissionCallback
) {
    PowerPermission.init(configuration)
        .requestPermissions(
            context = this,
            permissions = *permissions,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate,
            callback = callback
        )
}

/**
 * extension to simple usage about requesting permissions when all granted
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 * @param callback to return after execute requesting permissions all granted or not
 */
fun Fragment.askPermissionsAllGranted(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null,
    callback: (Boolean) -> Unit
) {
    PowerPermission.init(configuration)
        .requestPermissions(
            context = this,
            permissions = *permissions,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) { permissionResults ->
            callback(permissionResults.hasAllGranted())
        }
}
