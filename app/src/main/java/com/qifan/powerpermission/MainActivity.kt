package com.qifan.powerpermission

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.data.*
import com.qifan.powerpermission.databinding.ActivityMainBinding
import com.qifan.powerpermission.rationale.createDialogRationale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val resultText get() = binding.result
    private val requestButton get() = binding.request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestButton.setOnClickListener { requestPermissions() }
    }

    private fun requestPermissions() {
//        PowerPermission.init(this)
//            .requestPermissions(
//                Manifest.permission.CAMERA,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_CALENDAR,
//                rationaleDelegate = createDialogRationale(
//                    R.string.rational_title,
//                    Manifest.permission.CAMERA,
//                    getString(R.string.permission_rational, Manifest.permission.CAMERA)
//                )
//            ) { permissionResult ->
//                when {
//                    permissionResult.hasAllGranted() -> {
//                        doPermissionAllGrantedWork(permissionResult.granted())
//                    }
//                    permissionResult.hasRational() -> {
//                        doPermissionReasonWork(permissionResult.rational())
//                    }
//                    permissionResult.hasPermanentDenied() -> {
//                        doPermissionPermanentWork(permissionResult.permanentDenied())
//                    }
//                }
//            }
        askPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CALENDAR,
            rationaleDelegate = createDialogRationale(
                R.string.rational_title,
                Manifest.permission.CAMERA,
                getString(R.string.permission_rational, Manifest.permission.CAMERA)
            )
        ) { permissionResult ->
            when {
                permissionResult.hasAllGranted() -> {
                    doPermissionAllGrantedWork(permissionResult.granted())
                }
                permissionResult.hasRational() -> {
                    doPermissionReasonWork(permissionResult.rational())
                }
                permissionResult.hasPermanentDenied() -> {
                    doPermissionPermanentWork(permissionResult.permanentDenied())
                }
            }
        }
    }

    private fun doPermissionPermanentWork(permanentDenied: List<Permission>) {
        resultText.text = getString(R.string.permission_denied, permanentDenied)
    }

    private fun doPermissionReasonWork(rational: List<Permission>) {
        Log.d(this::class.java.simpleName, "reason work $rational")
    }

    private fun doPermissionAllGrantedWork(permissions: List<Permission>) {
        resultText.text = getString(R.string.permission_all_granted, permissions)
    }
}
