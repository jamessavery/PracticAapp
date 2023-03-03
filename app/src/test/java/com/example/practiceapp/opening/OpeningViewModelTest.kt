package com.example.practiceapp.opening

import com.example.data.StateSingleton
import com.example.data.StateSingletonImpl
import com.example.practiceapp.tools.RxRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class OpeningViewModelTest {

    val fakestateSingleton = FakeStateSingletonImpl


    @get:Rule
    val rxRule = RxRule()

    @Test
    fun flatMapExample() {
        Assert.assertEquals(fakestateSingleton.getTriggeredTing(), "testing")
//        val flatMapExample = openingtesting.flatMapExampleCode().test().assertComplete()
    }
}

// Not appropriate use of fakes..?
object FakeStateSingletonImpl : StateSingleton {
    override fun setTriggeredTing(ting: String) {
        TODO("Not yet implemented")
    }

    override fun getTriggeredTing(): String? {
        return "testing"
    }
}
