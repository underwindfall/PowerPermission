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

import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.PermissionCallback
import com.qifan.powerpermission.RequestCode
import com.qifan.powerpermission.core.extension.debug
import com.qifan.powerpermission.core.extension.transact
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

/**
 * internal manager to help dealing with runtime permissions
 */
class PowerPermissionManager internal constructor() {

    private var permissionFragment: PermissionFragment? = null

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal const val TAG_ACTIVITY = "[power_permission_fragment/activity]"

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal const val TAG_FRAGMENT =
            "[power_permission_fragment/com.qifan.powerpermission.fragment]"
    }

    private fun onAttachPermissionFragment(fragmentActivity: FragmentActivity): PermissionFragment {
        permissionFragment = if (permissionFragment == null) {
            PermissionFragment().apply {
                debug(
                    "Created new PermissionFragment for %s",
                    fragmentActivity::class.java.simpleName
                )
                fragmentActivity.transact {
                    add(this@apply, TAG_ACTIVITY)
                }
            }
        } else {
            debug(
                "Re-using PermissionFragment for %s",
                fragmentActivity::class.java.simpleName
            )
            permissionFragment
        }
        return permissionFragment ?: error("impossible!")
    }

    private fun onAttachPermissionFragment(fragment: Fragment): PermissionFragment {
        permissionFragment = if (permissionFragment == null) {
            PermissionFragment().apply {
                debug(
                    "Created new PermissionFragment for %s",
                    fragment::class.java.simpleName
                )
                fragment.transact {
                    add(this@apply, TAG_FRAGMENT)
                }
            }
        } else {
            debug(
                "Re-using PermissionFragment for  %s",
                fragment::class.java.simpleName
            )
            permissionFragment
        }
        return permissionFragment ?: error("impossible!")
    }

    private fun onDetachFragment() {
        permissionFragment?.release()
        permissionFragment = null
    }

    /**
     * All permission need to be requested
     * @param context context activity
     * @param permissions a list of permissions to be requested
     * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
     * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
     * @param callback to return after execute requesting permissions
     */
    fun requestPermissions(
        context: FragmentActivity,
        vararg permissions: Permission,
        requestCode: RequestCode = PERMISSION_REQUEST_CODE,
        rationaleDelegate: RationaleDelegate? = null,
        callback: PermissionCallback
    ) {
        check(permissions.isNotEmpty()) { "PowerPermission requires at least one input permission" }
        PermissionParams(
            permissions = permissions.toList(),
            requestCode = requestCode,
            callback = callback,
            rationaleDelegate = rationaleDelegate,
            cleanCallback = ::onDetachFragment
        ).apply {
            onAttachPermissionFragment(context).askedPermission(this)
        }
    }

    /**
     * All permission need to be requested
     * @param context context fragment
     * @param permissions a list of permissions to be requested
     * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
     * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
     * @param callback to return after execute requesting permissions
     */
    fun requestPermissions(
        context: Fragment,
        vararg permissions: Permission,
        requestCode: RequestCode = PERMISSION_REQUEST_CODE,
        rationaleDelegate: RationaleDelegate? = null,
        callback: PermissionCallback
    ) {
        check(permissions.isNotEmpty()) { "PowerPermission requires at least one input permission" }
        PermissionParams(
            permissions = permissions.toList(),
            requestCode = requestCode,
            callback = callback,
            rationaleDelegate = rationaleDelegate,
            cleanCallback = ::onDetachFragment
        ).apply {
            onAttachPermissionFragment(context).askedPermission(this)
        }
    }
}
