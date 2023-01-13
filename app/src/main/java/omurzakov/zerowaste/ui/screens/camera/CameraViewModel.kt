package omurzakov.zerowaste.ui.screens.camera

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.mlkit.vision.label.ImageLabel
import omurzakov.zerowaste.architecture.BaseViewModel
import omurzakov.zerowaste.ml.IMachineLearningModel

class CameraViewModel(private val mlModel: IMachineLearningModel) : BaseViewModel() {

    var detectedObjects by mutableStateOf<List<ImageLabel>>(emptyList())

    fun getImageLabels(bitmap: Bitmap?) {
        mlModel.processImage(bitmap!!).addOnSuccessListener { detectedObjects = it }
    }

    fun identifyCategory(obj: String): String {
        return when (obj.lowercase()) {
            "banana" -> "Biological waste"
            "sweatshirt" -> "Clothes"
            "pop bottle" -> "Plastic"
            "water bottle" -> "Plastic"
            "beer bottle" -> "Plastic"
            "lighter" -> "Plastic"
            "water jug" -> "Stained glass"
            "carton" -> "Beverage cartons"
            else -> "Paper"
        }
    }
}