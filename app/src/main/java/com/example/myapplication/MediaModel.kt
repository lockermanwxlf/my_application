package com.example.myapplication

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class MediaModel (
    var uri: Uri? = null
)