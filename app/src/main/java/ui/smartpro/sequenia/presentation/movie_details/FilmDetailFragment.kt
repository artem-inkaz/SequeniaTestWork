package ui.smartpro.sequenia.presentation.movie_details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber
import ui.smartpro.sequenia.R
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.databinding.ItemMovieDescriptionBinding
import ui.smartpro.sequenia.extensions.useCoilToLoadPhoto

class FilmDetailFragment : Fragment(R.layout.item_movie_description) {

    companion object {
        const val BUNDLE_EXTRA = "film"
    }

    private val binding: ItemMovieDescriptionBinding by viewBinding()

    private lateinit var filmBundle: Film

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.getParcelable<Film>(BUNDLE_EXTRA) != null) {
            filmBundle = arguments?.getParcelable<Film>(BUNDLE_EXTRA)!!
            filmBundle.let { film ->
                displayMovie(film)
            }
        }

        if (filmBundle.localized_name.isNullOrEmpty())
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "Нет названия"
        else (requireActivity() as AppCompatActivity).supportActionBar?.title =
            filmBundle.localized_name
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun displayMovie(film: Film) {
        with(binding) {
            root.context?.let { context ->
                film.image_url?.let {
                    useCoilToLoadPhoto(
                        root.context,
                        emptyImageTitleTv!!,
                        imageMovie,
                        it
                    )
                }
                Timber.tag("selectedPos").d(film.image_url)
                if (film.image_url == null) {
                    emptyImageTitleTv?.visibility = View.VISIBLE
                    emptyImageTitleTv?.text =
                        resources.getString(R.string.alert_description_download_image_movie)
                }
                nameMovie.text = film.localized_name
                descriptionMovie.text = film.description
                yearMovie.text = String.format(
                    resources.getString(R.string.tools_text_year_movie),
                    film.year
                )
                ratingMovie.text = String.format(
                    resources.getString(R.string.tools_text_rating_movie),
                    film.rating
                )
            }
        }
    }
}