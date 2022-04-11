package ui.smartpro.sequenia.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.ImageLoader
import coil.request.LoadRequest
import ui.smartpro.sequenia.R

fun useCoilToLoadPhoto(context: Context,textView: TextView, imageView: ImageView, imageLink: String) {
    val request = LoadRequest.Builder(context)
        .data(imageLink)
        .target(
            onStart = {},
            onSuccess = { result ->
                imageView.setImageDrawable(result)
            },
            onError = {
                imageView.setImageResource(R.drawable.ic_combined_shape)
                textView.text = context.getString(R.string.alert_description_download_image_movie)
                textView.visibility = View.VISIBLE
            }
        )
        .transformations(
//            CircleCropTransformation()
        )
        .size(100,100)
        .build()

    ImageLoader(context).execute(request)
}
