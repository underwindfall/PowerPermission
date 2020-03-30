package com.qifan.powerpermission.internal

import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentActivity
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.PermissionCallback
import com.qifan.powerpermission.RequestCode
import com.qifan.powerpermission.internal.extension.debug
import com.qifan.powerpermission.internal.extension.transact
import com.qifan.powerpermission.rationale.RationaleHandler

/**
 * internal manager to help dealing with runtime permissions
 * @param fragmentActivity An instance of FragmentActivity
 */
class PowerPermissionManager internal constructor(
    private val fragmentActivity: FragmentActivity
) {
    private var permissionFragment: PermissionFragment? = null

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal const val TAG_ACTIVITY = "[power_permission_fragment/activity]"

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal const val TAG_FRAGMENT = "[power_permission_fragment/fragment]"
    }

    private fun onAttachPermissionFragment(): PermissionFragment {
        permissionFragment = if (permissionFragment == null) {
            PermissionFragment().apply {
                debug("Created new PermissionFragment for Context")
                fragmentActivity.transact { add(this@apply, TAG_ACTIVITY) }
            }
        } else {
            debug("Re-using PermissionFragment for Context")
            permissionFragment
        }
        return permissionFragment ?: error("impossible!")
    }

    private fun onDetachFragment() {
        permissionFragment?.release()
    }

    /**
     * All permission need to be requested
     * @param permissions a list of permissions to be requested
     * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
     * @param rationaleHandler rationaleHandler to handle displaying reason interaction
     * @param callback to return after execute requesting permissions
     */
    fun requestPermissions(
        vararg permissions: Permission,
        requestCode: RequestCode = PERMISSION_REQUEST_CODE,
        rationaleHandler: RationaleHandler? = null,
        callback: PermissionCallback
    ) {
        PermissionParams(
            permissions = permissions.toList(),
            requestCode = requestCode,
            callback = callback,
            rationaleHandler = rationaleHandler
        ).apply {
            onAttachPermissionFragment().askedPermission(this)
        }
    }
}
