package com.carlosjimz87.installerapp

import android.content.Context
import android.os.Environment
import timber.log.Timber
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


object AssetsManager {
    private const val EXTENSION = ".apk"
    fun copyToExternal(context: Context) {
        try {
            copy(context)
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }


    fun get(context: Context, filename: String? = ""): String {
        return list(context, filename).first()
    }

    fun list(context: Context, filename: String? = ""): Array<String> {
        val files: Array<String>?
        try {
            files = if (filename?.isEmpty() == true) {
                context.assets.list("Files")?.filter { it.endsWith(EXTENSION) }?.toTypedArray()
            } else {
                context.assets.list("Files")?.filter { it.equals(filename) }?.toTypedArray()
            }
            Timber.d("Files in assets ${files?.size}")

            if (files == null) return emptyArray()

            return files
        } catch (e: IOException) {
            Timber.e(e.message)
            return emptyArray()
        }
    }

    private fun copy(context: Context) {

        for (filename in list(context)) {

            open(context, filename) { input ->
                Timber.d("Got $filename from assets")
                FileOutputStream(
                    "${Environment.getExternalStorageDirectory()}/$filename"
                ).use { output ->
                    Timber.d("Stream of $filename created in external.\nTrying to copy content")
                    copyFile(input, output)
                }
            }
        }
    }

    fun open(
        context: Context,
        filename: String,
        action: (InputStream) -> Unit
    ) {
        val assetManager = context.assets
        assetManager.open("Files/$filename").use { input ->
            action(input)
        }
    }

    @Throws(IOException::class)
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
    }
}