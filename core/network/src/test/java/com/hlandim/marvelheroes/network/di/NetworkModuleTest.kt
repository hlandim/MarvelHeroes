package com.hlandim.marvelheroes.network.di

import android.content.Context
import io.mockk.mockkClass
import org.junit.Rule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.check.checkKoinModules
import org.koin.test.mock.MockProviderRule
import org.koin.test.verify.verify

class NetworkModuleTest {
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz -> mockkClass(clazz) }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `verify koin configuration`() {
        networkModule.verify()
    }

    @Test
    fun `check koin module`() {
        checkKoinModules(listOf(networkModule)) {
            withInstance<Context>()
        }
    }
}
