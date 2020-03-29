package com.qifan.powerpermission.internal

import android.content.Context
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.internal.extension.debug
import com.qifan.powerpermission.internal.extension.transact
import kotlin.properties.Delegates.notNull

private const val PERMISSION_REQUEST_CODE = 1

internal typealias RequestCallback = (
    rationalList: List<String>,
    grantedList: List<String>,
    deniedList: List<String>
) -> Unit

class PermissionFragment : Fragment() {
    private var requestCallback: RequestCallback by notNull()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        debug("onAttach(%s)", context)
    }

    override fun onDetach() {
        debug("onDetach()")
        super.onDetach()
    }

    internal fun askedPermission(
        permissions: Array<out Permission>,
        requestCallback: RequestCallback
    ) {
        debug("askedPermission(%s)", permissions)
        this.requestCallback = requestCallback
        requestPermissions(permissions, PERMISSION_REQUEST_CODE)
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
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val grantedList = mutableListOf<Permission>()
            val deniedList = mutableListOf<Permission>()
            val rationalList = mutableListOf<Permission>()
            for ((index, result) in grantResults.withIndex()) {
                when {
                    result == PackageManager.PERMISSION_GRANTED -> {
                        grantedList.add(permissions[index])
                    }
                    shouldShowRequestPermissionRationale(permissions[index]) -> {
                        rationalList.add(permissions[index])
                    }
                    else -> {
                        deniedList.add(permissions[index])
                    }
                }
            }
            requestCallback(rationalList, grantedList, deniedList)
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
