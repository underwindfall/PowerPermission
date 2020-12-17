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
package com.qifan.powerpermission.rationale.delegate

import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.RequestCode
import com.qifan.powerpermission.core.extension.debug
import com.qifan.powerpermission.data.RationaleData

internal typealias PermissionRequest = (Array<out Permission>, RequestCode) -> Unit

/**
 * interface to delegate [RuntimePermission] decide which way to display rationale view
 */
interface RationaleDelegate {
    // essential data about rational data
    val data: RationaleData

    /**
     * function to invoke showing a rational view displaying when permissions have been refused
     * @param request callback to invoke request permission in PermissionFragment
     * @param requestCode request Permission CODE
     */
    fun showRationale(request: PermissionRequest, requestCode: RequestCode) {
        displayRationale(
            message = data.message,
            actionCallback = RationaleActionCallback { recheck ->
                if (recheck) {
                    onAcceptRecheckPermission(request, requestCode)
                } else {
                    onRefuseRecheckPermission()
                }
            },
            permission = data
                .getRationalePermission()
                .toTypedArray()
        )
    }

    /**
     * display a view to explain reason to user why request permissions
     * @param permission target rational permission or permissions
     * @param message reason about explaining why request permissions
     * @param actionCallback invoke to recheck permission or not when give `true` it will trigger
     * [onAcceptRecheckPermission] otherwise [onRefuseRecheckPermission]
     */
    fun displayRationale(
        vararg permission: Permission,
        message: String,
        actionCallback: RationaleActionCallback
    )

    private fun onAcceptRecheckPermission(
        requestPermission: PermissionRequest,
        requestCode: RequestCode
    ) {
        debug("Permissions %s will been asked again", data.getRationalePermission())
        onDismissView()
        requestPermission(
            data
                .getRationalePermission()
                .toTypedArray(),
            requestCode
        )
    }

    private fun onRefuseRecheckPermission() {
        debug("Permissions %s are denied", data.getRationalePermission())
        onDismissView()
    }

    /**
     * rational view to disappear
     */
    fun onDismissView()
}
