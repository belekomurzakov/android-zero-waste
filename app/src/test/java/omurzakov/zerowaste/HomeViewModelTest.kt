package omurzakov.zerowaste

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import omurzakov.zerowaste.communication.MockLocalRepositoryImpl
import omurzakov.zerowaste.communication.MockRemoteRepositoryImpl
import omurzakov.zerowaste.models.WasteType
import omurzakov.zerowaste.ui.screens.detail.DetailUiState
import omurzakov.zerowaste.ui.screens.detail.DetailViewModel
import omurzakov.zerowaste.utils.Constant
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun loadData_isCorrect(): Unit = runTest {
        //given
        var result: WasteType? = null
        val detailViewModel = DetailViewModel(MockRemoteRepositoryImpl(), MockLocalRepositoryImpl())

        //when
        withContext(Dispatchers.IO) { detailViewModel.loadData("Plastic") }

        detailViewModel.detailUiState.value.let {
            when (it) {
                is DetailUiState.Start -> assertTrue(false)
                is DetailUiState.Error -> assertTrue(false)
                is DetailUiState.Loading -> assertTrue(false)
                is DetailUiState.Success -> result = it.data
            }
        }

        //then
        Assert.assertEquals(
            WasteType(
                imageUrl = Constant.TEST_IMAGE,
                decompositionTime = "1 to 2",
                description = "Test Description",
                kills = 200,
                cans = 100
            ), result
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}