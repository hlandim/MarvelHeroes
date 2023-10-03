package com.hlandim.marvelheroes.di

import android.content.Context
import io.mockk.mockkClass
import org.junit.Rule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.check.checkKoinModules
import org.koin.test.mock.MockProviderRule
import org.koin.test.verify.verify

class MrModuleTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz -> mockkClass(clazz) }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `verify koin configuration`() {
        mhModule.verify()
    }

    @Test
    fun `check koin module`() {
        checkKoinModules(listOf(mhModule)) {
            withInstance<Context>()
        }
    }
}
