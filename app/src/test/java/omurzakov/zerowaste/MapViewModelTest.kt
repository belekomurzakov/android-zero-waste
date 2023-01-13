package omurzakov.zerowaste

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import omurzakov.zerowaste.communication.MockRemoteRepositoryImpl
import omurzakov.zerowaste.models.Can
import omurzakov.zerowaste.ui.screens.map.MapUiState
import omurzakov.zerowaste.ui.screens.map.MapViewModel
import omurzakov.zerowaste.utils.Constant
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun getData_no_category_isCorrect(): Unit = runTest {
        //given
        var result = listOf<Can>()
        val mapViewModel = MapViewModel(MockRemoteRepositoryImpl())

        //when
        withContext(Dispatchers.Default) { mapViewModel.getData("no-category") }

        mapViewModel.mapUiState.value.let {
            when (it) {
                MapUiState.Start -> assertTrue(false)
                is MapUiState.Error -> assertTrue(false)
                is MapUiState.Success -> result = it.data
            }
        }

        //then
        Assert.assertEquals(Constant.CANS_DIFF, result)
    }

    @Test
    fun getData_specific_category_isCorrect(): Unit = runTest {
        //given
        var result = listOf<Can>()
        val mapViewModel = MapViewModel(MockRemoteRepositoryImpl())

        //when
        withContext(Dispatchers.Default) { mapViewModel.getData("Clothes") }

        mapViewModel.mapUiState.value.let {
            when (it) {
                MapUiState.Start -> assertTrue(false)
                is MapUiState.Error -> assertTrue(false)
                is MapUiState.Success -> result = it.data
            }
        }

        //then
        Assert.assertEquals(Constant.CANS_CLOTHES, result)
        Dispatchers.resetMain()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}