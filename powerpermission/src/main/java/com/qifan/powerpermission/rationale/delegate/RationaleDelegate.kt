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
    //essential data about rational data
    val data: RationaleData

    /**
     * function to invoke showing a rational view displaying when permissions have been refused
     * @param request callback to invoke request permission in PermissionFragment
     * @param requestCode request Permission CODE
     */
    fun showRationale(request: PermissionRequest, requestCode: RequestCode) {
        displayRationale(
            permission = *data
                .getRationalePermission()
                .toTypedArray(),
            message = data.message,
            actionCallback = RationaleActionCallback { recheck ->
                if (recheck) {
                    onAcceptRecheckPermission(request, requestCode)
                } else {
                    onRefuseRecheckPermission()
                }
            }
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
