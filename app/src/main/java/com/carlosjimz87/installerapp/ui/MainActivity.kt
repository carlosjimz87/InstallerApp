package com.carlosjimz87.installerapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carlosjimz87.installerapp.R
import com.carlosjimz87.installerapp.managers.AssetsManager


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init
        val assetManager = AssetsManager(this)


//        if (verifyStoragePermissions(this)) {
        assetManager.copyToExternal()
//        }

//        APPS.forEach { (fileName, packageName) ->
//
//            Installer.install(this, fileName, packageName)
//        }
    }

//    private fun verifyStoragePermissions(activity: Activity?): Boolean {
//        // Check if we have write permission
//        val permission1 = ActivityCompat.checkSelfPermission(
//            activity!!,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//        val permission2 = ActivityCompat.checkSelfPermission(
//            activity,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        )
//        return when (permission1 * permission2) {
//            1 -> true
//            else -> false
//        }
//    }
}