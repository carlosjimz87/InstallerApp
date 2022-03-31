package com.carlosjimz87.installerapp.models

data class Download(
    val id: String,
    val filename: String,
    val path: String,
    val packageName: String
)