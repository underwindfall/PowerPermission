package com.qifan.powerpermission

import androidx.fragment.app.FragmentActivity
import com.qifan.powerpermission.core.PowerPermissionManager
import com.qifan.powerpermission.data.Configuration
import com.qifan.powerpermission.data.DefaultConfiguration
import com.qifan.powerpermission.data.PermissionResult
import kotlin.properties.Delegates.notNull

typealias Permission = String
typealias RequestCode = Int
typealias PermissionCallback = (PermissionResult) -> Unit

/**
 * Singleton to use as PowerPermission do initial work
 */
object PowerPermission {
    internal var configuration: Configuration by notNull()

    /**
     * Init PowerPermission to make everything prepare to work.
     *
     * @param activity An instance of FragmentActivity
     */
    fun init(
        activity: FragmentActivity,
        configuration: Configuration = DefaultConfiguration()
    ): PowerPermissionManager {
        this.configuration = configuration
        return PowerPermissionManager(activity)
    }
}
