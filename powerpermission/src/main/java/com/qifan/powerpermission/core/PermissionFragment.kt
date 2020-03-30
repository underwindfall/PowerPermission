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
