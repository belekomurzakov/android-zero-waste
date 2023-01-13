package omurzakov.zerowaste.models

import com.google.gson.annotations.SerializedName

data class WasteType(
    var description: String,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("decomposition_time")
    var decompositionTime: String,
    var kills: Int,
    var cans: Int
)
