package com.lamudi.phonefield

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CountriesTest(private val testCountry: Country) {

    @Test
    fun testCountry() {
        assertTrue(testCountry.displayName.isNotBlank())
    }

    companion object {

        @JvmStatic
        @Parameterized.Parameters
        fun parameters(): Collection<Country> = Countries.COUNTRIES
    }
}