package omurzakov.zerowaste.models

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem

data class Can(
    var id: String,
    var latitude: Float,
    var longitude: Float,
    var tid: Int,
    @SerializedName("type_waste_separated")
    var typeWasteSeparated: String?,
    @SerializedName("commodity_waste_separated")
    var commodityWasteSeparated: String,
    var volume: String?,
    var owner: String?,
    var name: String,
    var street: String?,
    var cp: Int,
    var isPublic: String?
) : ClusterItem {

    override fun getPosition(): LatLng {
        return LatLng(latitude.toDouble(), longitude.toDouble())
    }

    override fun getTitle(): String {
        return commodityWasteSeparated
    }

    override fun getSnippet(): String {
        return commodityWasteSeparated
    }
}