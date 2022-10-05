package com.jpndev.portfolio.utils.extensions

import android.content.res.AssetManager
import java.io.InputStream

/**
 * Reads contents of Asset File
 * @param fileName Name of asset file to read
 * @return Contents (in String) of asset file
 */
fun AssetManager.readAssetsFile(fileName: String) = open(fileName).readText()

/**
 * Reads text from input stream
 * @return Contents of InputStream
 */
private fun InputStream.readText() = bufferedReader().use { it.readText() }