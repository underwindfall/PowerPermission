package com.qifan.powerpermission.data

// data class PermissionResult(
//    internal val resultsMap: Map<Permission, GrantResult>
// ) {
//    internal constructor(
//        permissions: Set<Permission>,
//        grantResults: List<GrantResult>
//    ) : this(
//        permissions
//            .mapIndexed { index, permission ->
//                Pair(permission, grantResults[index])
//            }
//            .toMap()
//    )
//
//    internal constructor(
//        permissions: Set<Permission>,
//        grantResults: IntArray
//    ) : this(
//        permissions,
//        grantResults.mapGrantResults(permissions)
//    )
// }
