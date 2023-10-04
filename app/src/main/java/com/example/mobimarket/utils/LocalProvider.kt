package com.example.mobimarket.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.Log.DEBUG
import java.io.File
import java.io.InputStream

class LocalProvider(private val context: Context) {

    val TAG = "FileUtils"

    // Сохранить файл из потока данных
    fun saveFile(fileName: String, inputStream: InputStream): File? {
        val file = File(context.filesDir, fileName)
        return try {
            file.outputStream().use { output ->
                inputStream.copyTo(output)
                file
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Получить Uri файла по его имени
    fun getFileUri(fileName: String): Uri {
        val file = File(context.filesDir, fileName)
        return Uri.fromFile(file)
    }

    // Получить файл по его Uri
    fun getFileFromUri(uri: Uri): File? {
        return try {
            val filePath = uri.path
            if (filePath != null) {
                File(filePath)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getFile(context: Context, uri: Uri): File? {
        if (uri != null) {
            val path = getPath(context, uri)
            if (path != null && isLocal(path)) {
                return File(path)
            }
        }
        return null
    }

    fun getPath(context: Context, uri: Uri): String {
        val absolutePath = getLocalPath(context, uri)
        return absolutePath ?: uri.toString()
    }

    private fun getLocalPath(context: Context, uri: Uri): String? {
        if (DEBUG==3) {
            Log.d(
                TAG + " File -",
                "Authority: ${uri.authority}, " +
                        "Fragment: ${uri.fragment}, " +
                        "Port: ${uri.port}, " +
                        "Query: ${uri.query}, " +
                        "Scheme: ${uri.scheme}, " +
                        "Host: ${uri.host}, " +
                        "Segments: ${uri.pathSegments.toString()}"
            )
        }
        return null
    }

    fun isLocal(url: String?): Boolean {
        return url != null && !url.startsWith("http://") && !url.startsWith("https://")
    }
}