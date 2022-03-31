package com.carlosjimz87.installerapp.managers

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.carlosjimz87.installerapp.models.FileType
import com.carlosjimz87.installerapp.models.MFile
import java.io.File

class FileManager(
    val context: Context
) {

    fun getFilesFromPath(
        path: String,
        showHiddenFiles: Boolean = false,
        onlyFolders: Boolean = false
    ): List<File> {

        val file = File(path)

        file.listFiles()?.let { files ->
            return files
                .filter { showHiddenFiles || !it.name.startsWith(".") }
                .filter { !onlyFolders || it.isDirectory }
                .toList()
        }
        return emptyList()
    }

    fun getFileModelsFromFiles(files: List<File>): List<MFile> {
        return files.map {
            MFile(
                it.path,
                it.name,
                FileType.getFileType(it),
                convertFileSizeToMB(it.length()),
                it.extension,
                it.listFiles()?.size
                    ?: 0
            )
        }
    }

    fun convertFileSizeToMB(sizeInBytes: Long): Double {
        return (sizeInBytes.toDouble()) / (1024 * 1024)
    }

    fun Context.launchFileIntent(fileModel: MFile) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = FileProvider.getUriForFile(this, packageName, File(fileModel.path))
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivity(Intent.createChooser(intent, "Select Application"))
    }
}