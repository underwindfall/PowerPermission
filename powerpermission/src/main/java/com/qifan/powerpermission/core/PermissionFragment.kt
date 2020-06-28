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

import android.content.Context
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.core.extension.debug
import com.qifan.powerpermission.core.extension.transact
import com.qifan.powerpermission.data.PermissionResult
import kotlin.properties.Delegates.notNull

/**
 * Permission Fragment implemented silently into application for requesting permissions
 */
class PermissionFragment : Fragment() {
    private var permissionParams: PermissionParams by notNull()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        debug("onAttach(%s)", context)
    }

    override fun onDetach() {
        debug("onDetach()")
        super.onDetach()
    }

    internal fun askedPermission(params: PermissionParams) {
        val (permissions, code, _) = params
        debug("askedPermission(%s)", permissions.toString())
        requestPermissions(permissions.toTypedArray(), code)
        permissionParams = params
    }

    internal fun release() {
        if (parentFragment != null) {
            debug("Detaching PermissionFragment from parent Fragment %s", parentFragment)
            parentFragment?.transact {
                detach(this@PermissionFragment)
                remove(this@PermissionFragment)
            }
        } else if (activity != null) {
            debug("Detaching PermissionFragment from Activity %s", activity)
            activity?.transact {
                detach(this@PermissionFragment)
                remove(this@PermissionFragment)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val (_, code, callback, rationalHandler) = permissionParams
        if (requestCode == code) {
            val result = PermissionResult(
                fragment = this@PermissionFragment,
                permissions = permissions.toSet(),
                grantResults = grantResults
            )
            if (rationalHandler != null && rationalHandler.data.shouldInvokeRational(
                this,
                result
            )
            ) {
                rationalHandler.showRationale(::requestPermissions, code)
            } else {
                callback(result)
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
