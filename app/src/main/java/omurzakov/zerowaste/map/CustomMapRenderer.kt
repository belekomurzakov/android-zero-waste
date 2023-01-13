package omurzakov.zerowaste.map

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import omurzakov.zerowaste.R
import omurzakov.zerowaste.models.Can

class CustomMapRenderer(
    val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Can>,
    initialSelectedItem: Can?
) : DefaultClusterRenderer<Can>(context, map, clusterManager) {

    private val icons: MutableMap<Int, Bitmap> = mutableMapOf()
    private var initialSelectedItem: Can? = null

    init {
        this.initialSelectedItem = initialSelectedItem
    }

    override fun onBeforeClusterItemRendered(item: Can, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        if (initialSelectedItem != null) {
            markerOptions.title("").icon(
                    BitmapDescriptorFactory.fromBitmap(getIcon(item))
                )
            initialSelectedItem = null
        } else {
            markerOptions.title("").icon(
                    BitmapDescriptorFactory.fromBitmap(getIcon(item))
                )
        }
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Can>): Boolean {
        return cluster.size > 5
    }

    private fun getIcon(container: Can): Bitmap {
        if (!icons.containsKey(container.id.toInt())) {
            icons[container.id.toInt()] =
                MarkerUtil.createMarkerIconFromResource(context, R.drawable.pin)
        }
        return icons[container.id.toInt()]!!
    }
}