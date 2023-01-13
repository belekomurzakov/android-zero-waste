package omurzakov.zerowaste.ml

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.mlkit.vision.label.ImageLabel

class MockMachineLearningModelImpl : IMachineLearningModel {

    override fun processImage(bitmap: Bitmap?): Task<List<ImageLabel>> {
        val taskCompletionSource = TaskCompletionSource<List<ImageLabel>>()
        taskCompletionSource.setResult(
            listOf(
                ImageLabel("Banana", 0.9F, 1),
                ImageLabel("Bottle", 0.8F, 1)
            )
        )
        return taskCompletionSource.task
    }
}