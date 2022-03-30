package com.carlosjimz87.installerapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log

object Installer {
    fun install1(context: Context, filename: String, packageName: String) {
        val promptInstall = Intent(Intent.ACTION_VIEW)
        promptInstall.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        promptInstall.setDataAndType(
            Uri.parse("file:///android_asset/$filename"),
            "application/$packageName"
        )
        context.startActivity(promptInstall)
    }

    fun appInstalledOrNot(context: Context, packageName: String): Boolean {
        val pm = context.packageManager;
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            true
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("INSTALLER", e.message.toString())
            false
        }
    }
}