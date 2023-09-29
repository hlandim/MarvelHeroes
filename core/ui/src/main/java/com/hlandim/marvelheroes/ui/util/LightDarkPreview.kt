package com.hlandim.marvelheroes.ui.util

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

private const val DARK_MODE_PREVIEW_NAME = "Dark Mode"
private const val LIGHT_MODE_PREVIEW_NAME = "Light Mode"

/**
 * Created by Hugo Santos on 14/10/2022.
 */
@Preview(
    uiMode = UI_MODE_NIGHT_NO or UI_MODE_TYPE_NORMAL,
    name = LIGHT_MODE_PREVIEW_NAME,
    device = Devices.NEXUS_5,
    showBackground = true
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
    name = DARK_MODE_PREVIEW_NAME,
    showBackground = true,
)
annotation class LightDarkPreview
