package com.app.sms.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.app.sms.R
import com.app.sms.databinding.ActivityPermissionBinding
import com.app.sms.utils.CheckPermission
import com.app.sms.utils.CheckPermission.openSettings
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class PermissionActivity : AppCompatActivity() {

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if(permissions.entries.first().value == true){
                Timber.d("SMS Permission : Granted")
                openMainActivity()
            }else{ Timber.d("SMS Permission : Denied") }
        }

    private lateinit var binding: ActivityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission)

        binding.btAllow.setOnClickListener {
            allowPermission()
        }

    }

    private fun allowPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,  Manifest.permission.READ_SMS)){
                openSettings(this)
            }else{
                requestMultiplePermissions.launch(
                    arrayOf(
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS)

                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(CheckPermission.hasPermissions(this) ){ openMainActivity() }
    }

    private fun openMainActivity(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }


}