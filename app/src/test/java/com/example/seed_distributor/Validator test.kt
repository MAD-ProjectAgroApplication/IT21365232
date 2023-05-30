package com.example.seed_distributor

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest {

    @Test
    fun whenInputs() {


        var shopNumber = "0771168665"
        var seedName = "tomato"
        var price = "100"

        val result = Validator.validateShopNumber(shopNumber, seedName, price)

        assertThat(result).isEqualTo(true)
    }
}