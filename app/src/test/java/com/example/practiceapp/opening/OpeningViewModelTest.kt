package com.example.practiceapp.opening

import com.example.practiceapp.tools.RxRule
import org.junit.Rule
import org.junit.Test

class OpeningViewModelTest {

    @get:Rule
    val rxRule = RxRule()

    val openingtesting = OpeningViewModel()

    @Test
    fun flatMapExample() {
        val flatMapExample = openingtesting.flatMapExampleCode().test().assertComplete()
    }
}
