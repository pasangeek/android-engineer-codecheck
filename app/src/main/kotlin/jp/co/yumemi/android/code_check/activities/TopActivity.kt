/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.activities

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import java.util.*

/**
 * This is the top-level activity in the Android application.
 * It serves as the entry point for the user interface and contains the main layout.
 * The activity is annotated with @AndroidEntryPoint for dependency injection using Hilt.
 */
@AndroidEntryPoint
class TopActivity : AppCompatActivity(R.layout.activity_top) {


}
