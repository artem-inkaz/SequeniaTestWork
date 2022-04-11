package ui.smartpro.sequenia.presentation.main.filmadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import ui.smartpro.sequenia.R
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.databinding.ItemMovieViewHolderBinding
import ui.smartpro.sequenia.utils.useCoilToLoadPhoto

class FilmsViewHolder(
    private val binding: ItemMovieViewHolderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): FilmsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMovieViewHolderBinding.inflate(inflater, parent, false)
            return FilmsViewHolder(binding)
        }
    }

    fun bind(film: Film) {
        with(binding) {
            root.context.let { context ->
                film.image_url?.let {
                    useCoilToLoadPhoto(
                        root.context,
                        emptyImageTitleTv,
                        imageMovie,
                        it
                    )
                }
                Timber.tag("selectedPos").d(film.image_url)
                if (film.image_url == null) {
                    emptyImageTitleTv.text =
                        context.getString(R.string.alert_description_download_image_movie)
                    emptyImageTitleTv.visibility = View.VISIBLE
                }
                emptyImageDescriptionTv.text = film.localized_name
            }
        }
    }
}