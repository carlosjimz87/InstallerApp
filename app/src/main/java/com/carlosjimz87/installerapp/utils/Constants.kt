package com.carlosjimz87.installerapp.utils

import android.Manifest

object Constants {

    const val APP_EXTENSION = ".apk"
    val APPS = mapOf(
        "Files/other_app.apk" to "com.onthespot.androidplayer",
        "Files/signed_app.apk" to "com.onthespot.system"
    )
    const val REQUEST_EXTERNAL_STORAGE = 1
    val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}