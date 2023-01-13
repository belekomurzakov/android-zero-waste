package omurzakov.zerowaste.ml

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.label.ImageLabel

interface IMachineLearningModel {
    fun processImage(bitmap: Bitmap?): Task<List<ImageLabel>>
}