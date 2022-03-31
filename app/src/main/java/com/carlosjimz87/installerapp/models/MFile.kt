package com.carlosjimz87.installerapp.models

data class MFile(
    val path: String,
    val filename: String,
    val fileType: FileType,
    val sizeInMB: Double,
    val extension: String = "",
    val subFiles: Int = 0,
    val packageName: String? = "",
    val id: String? = "",
)