package com.hlandim.marvelheroes.ui.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class UiText {
    /**
     * Any dynamic string.
     * @property value the string value.
     */
    data class DynamicString(val value: String) : UiText()

    /**
     * Util class used to pass string to compose UI Screens.
     * @param resId the String resource id.
     * @param args the String arguments.
     */
    @Suppress("OutdatedDocumentation")
    class StringResource(
        @StringRes val resId: Int,
        val args: ImmutableList<Any> = persistentListOf<String>(),
    ) : UiText()

    /**
     * Returns the string already converted from the initial resource.
     */
    @Composable
    @Suppress("SpreadOperator")
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args.toTypedArray())
        }
    }
}
