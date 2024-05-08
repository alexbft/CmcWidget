package com.example.widgettest

import android.content.Context
import java.io.InputStream
import java.util.Properties

class MyAppSettings(context: Context) {
    val cmcProxyHost: String
    val cmcProxyPort: Int
    val cmcProxyClientKey: String

    init {
        val rawResource: InputStream = context.resources.openRawResource(R.raw.app)
        val properties = Properties()
        properties.load(rawResource)
        cmcProxyHost = properties.getProperty("cmc-proxy-host")
        cmcProxyPort = properties.getProperty("cmc-proxy-port").toInt()
        cmcProxyClientKey = properties.getProperty("cmc-proxy-client-key")
    }
}