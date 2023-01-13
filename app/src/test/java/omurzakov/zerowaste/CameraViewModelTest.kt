package omurzakov.zerowaste

import android.graphics.Bitmap
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import omurzakov.zerowaste.ml.MockMachineLearningModelImpl
import omurzakov.zerowaste.ui.screens.camera.CameraViewModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CameraViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun identifyCategory_isCorrect() {
        //given
        val cameraViewModel = CameraViewModel(MockMachineLearningModelImpl())

        //when + then
        Assert.assertEquals("Biological waste", cameraViewModel.identifyCategory("Banana"))
        Assert.assertEquals("Clothes", cameraViewModel.identifyCategory("Sweatshirt"))
        Assert.assertEquals("Plastic", cameraViewModel.identifyCategory("Pop bottle"))
        Assert.assertEquals("Stained glass", cameraViewModel.identifyCategory("Water jug"))
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}