package com.qifan.powerpermission

import androidx.fragment.app.FragmentActivity
import com.qifan.powerpermission.data.PermissionResult
import com.qifan.powerpermission.internal.PowerPermissionManager

typealias Permission = String
typealias RequestCode = Int
typealias PermissionCallback = (PermissionResult) -> Unit

/**
 * Singleton to use as PowerPermission do initial work
 */
object PowerPermission {
    /**
     * Init PowerPermission to make everything prepare to work.
     *
     * @param activity An instance of FragmentActivity
     */
    fun init(activity: FragmentActivity) = PowerPermissionManager(activity)
}
