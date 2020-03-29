package com.qifan.powerpermission.internal

import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentActivity
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.PowerPermission.isAllGranted
import com.qifan.powerpermission.internal.extension.debug
import com.qifan.powerpermission.internal.extension.transact

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

    fun requestPermission(vararg permissions: Permission, requestCallback: RequestCallback) {
        if (fragmentActivity.isAllGranted(*permissions)) {
            onDetachFragment()
            return
        } else {
            onAttachPermissionFragment().askedPermission(permissions, requestCallback)
        }
    }
}
