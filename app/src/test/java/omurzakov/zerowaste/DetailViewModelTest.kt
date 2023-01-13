package omurzakov.zerowaste

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import omurzakov.zerowaste.communication.MockLocalRepositoryImpl
import omurzakov.zerowaste.communication.MockRemoteRepositoryImpl
import omurzakov.zerowaste.ui.screens.detail.DetailViewModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun alignCategory_isCorrect() {
        //given
        val detailViewModel = DetailViewModel(MockRemoteRepositoryImpl(), MockLocalRepositoryImpl())

        //when + then
        listOf("Cans", "Plastic", "Beverage cartons").forEach {
            Assert.assertEquals(
                "Plastic, beverage cartons and cans", detailViewModel.alignCategory(it)
            )
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}