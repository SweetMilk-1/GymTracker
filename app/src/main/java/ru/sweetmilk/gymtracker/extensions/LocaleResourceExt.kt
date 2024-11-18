package ru.sweetmilk.gymtracker.extensions

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

fun Context.getLocaleResources(): Resources {
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(Locale("ru"))
    return createConfigurationContext(configuration).resources
}