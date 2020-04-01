package com.qifan.powerpermission.livedata

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.RequestCode
import com.qifan.powerpermission.askPermissions
import com.qifan.powerpermission.askPermissionsAllGranted
import com.qifan.powerpermission.core.PERMISSION_REQUEST_CODE
import com.qifan.powerpermission.data.Configuration
import com.qifan.powerpermission.data.DefaultConfiguration
import com.qifan.powerpermission.data.PermissionResult
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

/**
 * extension to simple usage about requesting permissions
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 */
fun AppCompatActivity.observeAskPermissions(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): MutableLiveData<PermissionResult> {
    val mLiveData = MutableLiveData<PermissionResult>()
    askPermissions(
        permissions = *permissions,
        configuration = configuration,
        requestCode = requestCode,
        rationaleDelegate = rationaleDelegate
    ) { result ->
        mLiveData.postValue(result)
    }
    return mLiveData
}

/**
 * extension to simple usage about requesting permissions when all granted
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 */
fun AppCompatActivity.observeAskPermissionsAllGranted(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): MutableLiveData<Boolean> {
    val mLiveData = MutableLiveData<Boolean>()
    askPermissionsAllGranted(
        permissions = *permissions,
        configuration = configuration,
        requestCode = requestCode,
        rationaleDelegate = rationaleDelegate
    ) { allGranted ->
        mLiveData.postValue(allGranted)
    }
    return mLiveData
}

/**
 * extension to simple usage about requesting permissions
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 */
fun Fragment.observeAskPermissions(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): MutableLiveData<PermissionResult> {
    val mLiveData = MutableLiveData<PermissionResult>()
    askPermissions(
        permissions = *permissions,
        configuration = configuration,
        requestCode = requestCode,
        rationaleDelegate = rationaleDelegate
    ) { result ->
        mLiveData.postValue(result)
    }
    return mLiveData
}

/**
 * extension to simple usage about requesting permissions when all granted
 * @param permissions a list of permissions to be requested
 * @param configuration custom settings for whole permission manager
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 */
fun Fragment.observeAskPermissionsAllGranted(
    vararg permissions: Permission,
    configuration: Configuration = DefaultConfiguration(),
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null
): MutableLiveData<Boolean> {
    val mLiveData = MutableLiveData<Boolean>()
    askPermissionsAllGranted(
        permissions = *permissions,
        configuration = configuration,
        requestCode = requestCode,
        rationaleDelegate = rationaleDelegate
    ) { allGranted ->
        mLiveData.postValue(allGranted)
    }
    return mLiveData
}
