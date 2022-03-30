package com.carlosjimz87.installerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

val APPS = mapOf(
    "other_app.apk" to "com.onthespot.androidplayer",
    "signed_app.apk" to "com.onthespot.system"
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        APPS.forEach { (filename, packageName) ->

            Installer.method(InstallMethod.PROVIDER).install(
                this,
                filename = filename,
                packageName = packageName
            )
        }

        APPS.forEach { (_, packageName) ->
            Installer.appInstalledOrNot(this, packageName)
        }
    }

}