package com.qifan.powerpermission

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.core.PERMISSION_REQUEST_CODE
import com.qifan.powerpermission.data.Configuration
import com.qifan.powerpermission.data.DefaultConfiguration
import com.qifan.powerpermission.data.PermissionResult
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate
import io.reactivex.Observable

/**
 * extension to simple usage about requesting permissions
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 * @return [Observable] permission result
 */
fun AppCompatActivity.askPermissions(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Observable<PermissionResult> {
    checkMainThread()
    return Observable.create { emitter ->
        askPermissions(
            permissions = *permissions,
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) {
            emitter.onNext(it)
        }
    }
}

/**
 * extension to simple usage about requesting permissions when all granted
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 * @return [Observable] true means all permissions granted otherwise not
 */
fun AppCompatActivity.askPermissionsAllGranted(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Observable<Boolean> {
    return Observable.create { emitter ->
        askPermissionsAllGranted(
            permissions = *permissions,
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) { allGranted ->
            emitter.onNext(allGranted)
        }
    }
}

/**
 * extension to simple usage about requesting permissions
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 * @return [Observable] permission result
 */
fun Fragment.askPermissions(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Observable<PermissionResult> {
    checkMainThread()
    return Observable.create { emitter ->
        askPermissions(
            permissions = *permissions,
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) {
            emitter.onNext(it)
        }
    }
}

/**
 * extension to simple usage about requesting permissions when all granted
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 * @return [Observable] true means all permissions granted otherwise not
 */
fun Fragment.askPermissionsAllGranted(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Observable<Boolean> {
    return Observable.create { emitter ->
        askPermissionsAllGranted(
            permissions = *permissions,
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) { allGranted ->
            emitter.onNext(allGranted)
        }
    }
}
