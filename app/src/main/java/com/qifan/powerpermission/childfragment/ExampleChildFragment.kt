package com.qifan.powerpermission.childfragment

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
import com.qifan.powerpermission.databinding.FragmentChildExampleBinding
import com.qifan.powerpermission.rationale.createDialogRationale
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

class ExampleChildFragment : Fragment() {
    private lateinit var binding: FragmentChildExampleBinding
    private val requestButton get() = binding.request
    private val resultText get() = binding.result
    private val dialogRationaleDelegate: RationaleDelegate by lazy {
        createDialogRationale(
            R.string.rational_title,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            getString(
                R.string.permission_rational,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChildExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestButton.setOnClickListener { requestPermissions() }
        (activity as AppCompatActivity?)?.supportActionBar?.setTitle(R.string.button_request_child_fragment)
    }

    private fun requestPermissions() {
        askPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
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
