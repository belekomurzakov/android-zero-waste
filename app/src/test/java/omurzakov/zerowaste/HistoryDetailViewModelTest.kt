package omurzakov.zerowaste

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import omurzakov.zerowaste.communication.MockLocalRepositoryImpl
import omurzakov.zerowaste.models.UtilizedItem
import omurzakov.zerowaste.ui.screens.historydetail.HistoryDetailUiState
import omurzakov.zerowaste.ui.screens.historydetail.HistoryDetailViewModel
import omurzakov.zerowaste.utils.Constant
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class HistoryDetailViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getItem_isCorrect(): Unit = runTest {
        //given
        var result: UtilizedItem? = null
        val historyDetailViewModel = HistoryDetailViewModel(MockLocalRepositoryImpl())

        //when
        withContext(Dispatchers.Default) { historyDetailViewModel.getItem(2) }

        historyDetailViewModel.uiState.value.let {
            when (it) {
                HistoryDetailUiState.Start -> assertTrue(false)
                is HistoryDetailUiState.Error -> assertTrue(false)
                HistoryDetailUiState.Loading -> assertTrue(false)
                is HistoryDetailUiState.Success -> result = it.data
            }
        }

        //then
        Assert.assertEquals(Constant.UT_ITEM, result)
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}