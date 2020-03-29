package com.qifan.powerpermission.data

import androidx.annotation.CheckResult
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.internal.PermissionFragment
import com.qifan.powerpermission.internal.extension.isGranted
import com.qifan.powerpermission.internal.extension.isPermanentDenied
import com.qifan.powerpermission.internal.extension.isRational

/**
 * permission result wrapped callback essential data
 * @param resultsSet set of permissions
 */
data class PermissionResult(
    internal val resultsSet: Set<PermissionData>
) {
    internal constructor(
        fragment: PermissionFragment,
        permissions: Set<Permission>,
        grantResults: IntArray
    ) : this(
        permissions.mapIndexed { index, permission ->
            val grantResult: Int = grantResults[index]
            PermissionData(
                data = permission,
                grantResult = fragment.asGrantResult(grantResult, permission)
            )
        }
            .toSet()
    )
}

/**
 * Wrapped essential permission data information
 * @param data simple declaration permission such as [android.Manifest.permission.CAMERA].
 * @param grantResult [GrantResult] such as [GrantResult.GRANTED] etc.
 */
data class PermissionData(
    val data: Permission,
    val grantResult: GrantResult
) {
    override fun hashCode(): Int {
        return data.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other != null &&
                other is PermissionData &&
                this.data == other.data
    }
}

/** @return `true` if given permissions were granted. */
@CheckResult
fun PermissionResult.hasAllGranted(): Boolean {
    return resultsSet
        .asSequence()
        .all { it.isGranted() }
}

/** @return `true` if given permissions have rational permissions. */
@CheckResult
fun PermissionResult.hasRational(): Boolean {
    return resultsSet
        .asSequence()
        .any { it.isRational() }
}

/** @return `true` if given permissions have permanent denied permissions. */
@CheckResult
fun PermissionResult.hasPermanentDenied(): Boolean {
    return resultsSet
        .asSequence()
        .any { it.isPermanentDenied() }
}

/** @return a list of all required permissions. */
@CheckResult
fun PermissionResult.requiredPermissions(): List<Permission> {
    return resultsSet
        .asSequence()
        .map { it.data }
        .toList()
}

/** @return a list of all granted permissions. */
@CheckResult
fun PermissionResult.granted(): List<Permission> {
    return resultsSet.filter { it.isGranted() }
        .map { it.data }
}

/** @return a list of all rational permissions. */
@CheckResult
fun PermissionResult.rational(): List<Permission> {
    return resultsSet.filter { it.isRational() }
        .map { it.data }
}

/** @return a list of all denied permissions. */
@CheckResult
fun PermissionResult.permanentDenied(): List<Permission> {
    return resultsSet.filter { it.isPermanentDenied() }
        .map { it.data }
}
