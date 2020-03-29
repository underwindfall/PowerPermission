package com.qifan.powerpermission

import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.CheckResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.qifan.powerpermission.internal.PowerPermissionManager

typealias Permission = String

object PowerPermission {

    fun init(activity: FragmentActivity) = PowerPermissionManager(activity)

    /**
     * helper function to check all permissions are granted or not.
     * @param permissions list of permissions e.g.[android.Manifest.permission.CALL_PHONE]
     * @return `true` if ALL given [permissions] have been granted.
     * */
    @CheckResult
    fun Context.isAllGranted(vararg permissions: Permission): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}
