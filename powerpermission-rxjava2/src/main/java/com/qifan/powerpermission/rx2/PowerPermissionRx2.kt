/**
 * Copyright (C) 2020 by Qifan YANG (@underwindfall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qifan.powerpermission.rx2

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
import io.reactivex.Observable

/**
 * extension to simple usage about requesting permissions
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 * @return [Observable] permission result
 */
fun AppCompatActivity.askPermissionsRx(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Observable<PermissionResult> {
    checkMainThread()
    return Observable.create { emitter ->
        askPermissions(
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate,
            callback = { emitter.onNext(it) },
            permissions = permissions
        )
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
fun AppCompatActivity.askPermissionsAllGrantedRx(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Observable<Boolean> {
    return Observable.create { emitter ->
        askPermissionsAllGranted(
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate,
            callback = { allGranted: Boolean ->
                emitter.onNext(allGranted)
            },
            permissions = permissions
        )
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
fun Fragment.askPermissionsRx(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Observable<PermissionResult> {
    checkMainThread()
    return Observable.create { emitter ->
        askPermissions(
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate,
            callback = { emitter.onNext(it) },
            permissions = permissions,
        )
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
fun Fragment.askPermissionsAllGrantedRx(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): Observable<Boolean> {
    return Observable.create { emitter ->
        askPermissionsAllGranted(
            configuration = configuration,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate,
            callback = { allGranted: Boolean ->
                emitter.onNext(allGranted)
            },
            permissions = permissions
        )
    }
}
