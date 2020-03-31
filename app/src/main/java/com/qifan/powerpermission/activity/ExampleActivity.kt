package com.qifan.powerpermission.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.PowerPermission
import com.qifan.powerpermission.R
import com.qifan.powerpermission.askPermissions
import com.qifan.powerpermission.data.*
import com.qifan.powerpermission.databinding.ActivityExampleBinding
import com.qifan.powerpermission.rationale.createDialogRationale
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

class ExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExampleBinding
    private val requestButton get() = binding.request
    private val resultText get() = binding.result


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestButton.setOnClickListener { requestPermissions() }
    }

    private fun requestPermissions() {
        PowerPermission.init(this)
            .requestPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CALENDAR
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
