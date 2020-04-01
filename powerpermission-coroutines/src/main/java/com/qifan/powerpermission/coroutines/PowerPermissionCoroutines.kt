package com.qifan.powerpermission.coroutines

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.RequestCode
import com.qifan.powerpermission.askPermissions
import com.qifan.powerpermission.askPermissionsAllGranted
import com.qifan.powerpermission.core.PERMISSION_REQUEST_CODE
import com.qifan.powerpermission.data.Configuration
import com.qifan.powerpermission.data.DefaultConfiguration
import com.qifan.powerpermission.data.PermissionResult
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * extension to simple usage about requesting permissions
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 */
suspend fun AppCompatActivity.awaitAskPermissions(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): PermissionResult {
    checkMainThread()
    return suspendCancellableCoroutine { continuation ->
        askPermissions(
            permissions = *permissions,
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) { result ->
            continuation.resume(result)
        }
    }
}

/**
 * extension to simple usage about requesting permissions when all granted
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 */
suspend fun AppCompatActivity.awaitAskPermissionsAllGranted(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Boolean {
    return suspendCancellableCoroutine { continuation ->
        askPermissionsAllGranted(
            permissions = *permissions,
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) { allGranted ->
            continuation.resume(allGranted)
        }
    }
}

/**
 * extension to simple usage about requesting permissions
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 */
suspend fun Fragment.awaitAskPermissions(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): PermissionResult {
    checkMainThread()
    return suspendCancellableCoroutine { continuation ->
        askPermissions(
            permissions = *permissions,
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) { result ->
            continuation.resume(result)
        }
    }
}

/**
 * extension to simple usage about requesting permissions when all granted
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 */
suspend fun Fragment.awaitAskPermissionsAllGranted(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Boolean {
    return suspendCancellableCoroutine { continuation ->
        askPermissionsAllGranted(
            permissions = *permissions,
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) { allGranted ->
            continuation.resume(allGranted)
        }
    }
}
