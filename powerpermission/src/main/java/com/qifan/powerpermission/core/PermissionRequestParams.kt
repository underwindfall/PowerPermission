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
package com.qifan.powerpermission.core

import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.PermissionCallback
import com.qifan.powerpermission.RequestCode
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

/**
 * Default Permission request code
 */
const val PERMISSION_REQUEST_CODE = 1

internal typealias CleanCallback = () -> Unit

internal data class PermissionParams(
    val permissions: List<Permission>,
    val requestCode: RequestCode,
    val callback: PermissionCallback,
    val rationaleDelegate: RationaleDelegate?,
    val cleanCallback: CleanCallback
)
