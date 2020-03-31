package com.qifan.powerpermission.fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.R
import com.qifan.powerpermission.askPermissions
import com.qifan.powerpermission.data.*
import com.qifan.powerpermission.databinding.FragmentExampleBinding
import com.qifan.powerpermission.rationale.createDialogRationale
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

class ExampleFragment : Fragment() {
    private lateinit var binding: FragmentExampleBinding
    private val requestButton get() = binding.request
    private val resultText get() = binding.result
    private val dialogRationaleDelegate: RationaleDelegate by lazy {
        createDialogRationale(
            R.string.rational_title,
            Manifest.permission.READ_CALENDAR,
            getString(R.string.permission_rational, Manifest.permission.CAMERA)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestButton.setOnClickListener { requestPermissions() }
        (activity as AppCompatActivity?)?.supportActionBar?.setTitle(R.string.button_request_fragment)
    }

    private fun requestPermissions() {
        askPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CALENDAR,
            rationaleDelegate = dialogRationaleDelegate
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
        resultText.text = getString(R.string.permission_rational, rational)
    }

    private fun doPermissionAllGrantedWork(permissions: List<Permission>) {
        resultText.text = getString(R.string.permission_all_granted, permissions)
    }
}
