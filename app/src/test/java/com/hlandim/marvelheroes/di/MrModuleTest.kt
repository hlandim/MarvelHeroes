package com.hlandim.marvelheroes.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import io.mockk.mockkClass
import org.junit.Rule
import org.junit.Test
import org.koin.test.check.checkKoinModules
import org.koin.test.mock.MockProviderRule

class MrModuleTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz -> mockkClass(clazz, relaxed = true) }

    @Test
    fun `check koin module`() {
        checkKoinModules(listOf(mhModule)) {
            withInstance<Context>()
            withInstance<SavedStateHandle>()
        }
    }
}
