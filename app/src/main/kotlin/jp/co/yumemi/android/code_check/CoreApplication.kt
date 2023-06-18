package jp.co.yumemi.android.code_check

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
/**
 * Application class for the core module.
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class CoreApplication :Application(){
    // custom application code goes here
}