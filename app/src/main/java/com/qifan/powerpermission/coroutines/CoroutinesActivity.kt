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
package com.qifan.powerpermission.coroutines

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.R
import com.qifan.powerpermission.data.PermissionResult
import com.qifan.powerpermission.data.granted
import com.qifan.powerpermission.data.hasAllGranted
import com.qifan.powerpermission.data.hasPermanentDenied
import com.qifan.powerpermission.data.hasRational
import com.qifan.powerpermission.data.permanentDenied
import com.qifan.powerpermission.data.rational
import com.qifan.powerpermission.databinding.ActivityCoroutinesExmapleBinding
import com.qifan.powerpermission.rationale.createDialogRationale
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CoroutinesActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var binding: ActivityCoroutinesExmapleBinding
    private val simpleRequestButton get() = binding.simpleRequest
    private val resultText get() = binding.result
    private val dialogRationaleDelegate: RationaleDelegate by lazy {
        createDialogRationale(
            R.string.rational_title,
            Manifest.permission.CAMERA,
            getString(R.string.permission_rational, Manifest.permission.CAMERA)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesExmapleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.button_request_coroutines)
        setupRequestButton()
    }

    private fun setupRequestButton() {
        simpleRequestButton.setOnClickListener {
            launch {
                val result = awaitAskPermissions(
                    Manifest.permission.CAMERA,
                    rationaleDelegate = dialogRationaleDelegate
                )
                handlePermissionRequest(result)
            }
        }
    }

    private fun handlePermissionRequest(permissionResult: PermissionResult) {
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

    private fun doPermissionPermanentWork(permanentDenied: List<Permission>) {
        resultText.text = getString(R.string.permission_denied, permanentDenied)
    }

    private fun doPermissionReasonWork(rational: List<Permission>) {
        resultText.text = getString(R.string.permission_rational, rational)
    }

    private fun doPermissionAllGrantedWork(permissions: List<Permission>) {
        resultText.text = getString(R.string.permission_all_granted, permissions)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
