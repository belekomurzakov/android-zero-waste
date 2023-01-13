package omurzakov.zerowaste.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import omurzakov.zerowaste.R
import omurzakov.zerowaste.models.Can

class MarkerUtil {

    companion object {
        fun createMarkerIconFromResource(context: Context, resource: Int): Bitmap {
            val drawable = ContextCompat.getDrawable(context, resource)
            drawable!!.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

            val bm = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bm)
            drawable.draw(canvas)
            return bm
        }

        fun createCustomMarkerFromLayout(
            context: Context,
            container: Can,
            makeLarge: Boolean
        ): Bitmap {
            val layout = if (makeLarge) R.layout.map_marker_icon_large else R.layout.map_marker_icon
            val markerView = LayoutInflater.from(context).inflate(layout, null)
            val cardView = markerView.findViewById<CardView>(R.id.cardView)
            val textView = markerView.findViewById<TextView>(R.id.letterText)

            textView.text = container.title

            cardView.setCardBackgroundColor(
                if (container.commodityWasteSeparated == "paper") {
                    context.getColor(R.color.purple_200)
                } else {
                    context.getColor(R.color.purple_700)
                }
            )

            markerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            markerView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)
            markerView.buildDrawingCache()

            val returnedBitmap = Bitmap.createBitmap(
                markerView.measuredWidth,
                markerView.measuredHeight,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(returnedBitmap)
            val drawable: Drawable = markerView.background
            drawable.draw(canvas)
            markerView.draw(canvas)
            return returnedBitmap
        }

        fun createBitmapBasedOnType(context: Context): Bitmap {
            return createMarkerIconFromResource(context, R.drawable.ic_pin)
        }
    }
}