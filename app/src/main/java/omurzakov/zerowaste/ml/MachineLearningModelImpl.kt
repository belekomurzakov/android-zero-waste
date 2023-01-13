package omurzakov.zerowaste.ml

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions
import omurzakov.zerowaste.utils.Constant.mlModel

class MachineLearningModelImpl : IMachineLearningModel {

    override fun processImage(bitmap: Bitmap?): Task<List<ImageLabel>> {
        val localModel = LocalModel.Builder().setAssetFilePath(mlModel).build()

        val customImageLabelerOptions =
            CustomImageLabelerOptions
                .Builder(localModel)
                .setConfidenceThreshold(0.5f)
                .setMaxResultCount(5)
                .build()

        val labeler = ImageLabeling.getClient(customImageLabelerOptions)

        return labeler.process(InputImage.fromBitmap(bitmap!!, 0))
    }
}