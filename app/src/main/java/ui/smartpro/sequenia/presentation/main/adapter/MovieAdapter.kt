package ui.smartpro.sequenia.presentation.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber
import ui.smartpro.sequenia.R
import ui.smartpro.sequenia.R.drawable.item_background_press
import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.databinding.ItemGenresViewHolderBinding
import ui.smartpro.sequenia.presentation.main.filmadapter.FilmsAdapter
import ui.smartpro.sequenia.presentation.main.filmadapter.OnFilmClickListener
import ui.smartpro.sequenia.utils.SharedPreferencesHelper
import kotlin.math.absoluteValue

class MovieAdapter(
    private val onFilmClickListener: OnFilmClickListener,
    var onGenreItemClick: (
        (item: Genre, position: Int) -> Unit)? = null,

    ) : ListAdapter<Genre, RecyclerView.ViewHolder>(GenreDiff),KoinComponent {

    companion object {
        private const val HEADER_GENRES_TYPE = 0
        private const val HEADER_FILMS_TYPE = 1
        private const val GENRES_TYPE = 2
        private const val FILM_TYPE = 3
    }
    private val sharePref by inject<SharedPreferencesHelper>()
    var selectedPos = -1
    var prevSelectedPos = -1

    var data: List<Genre> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var dataPosition: Int? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var filmList: List<Film> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> {
                HEADER_GENRES_TYPE
            }
            data.size + 1 -> {
                HEADER_FILMS_TYPE
            }
            itemCount - 1 -> FILM_TYPE
            else -> GENRES_TYPE
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            HEADER_GENRES_TYPE -> {
                HeaderViewHolder.create(parent)
            }
            GENRES_TYPE -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemGenresViewHolderBinding.inflate(inflater, parent, false)
                GenresViewHolder(binding)
            }
            HEADER_FILMS_TYPE -> {
                HeaderFilmsViewHolder.create(parent)
            }
            FILM_TYPE -> {
                FilmTypeViewHolder.create(parent)
            }
            else -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemGenresViewHolderBinding.inflate(inflater, parent, false)
                GenresViewHolder(binding)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HEADER_GENRES_TYPE -> {
                (holder as HeaderViewHolder).bind()
            }
            GENRES_TYPE -> {
                (holder as GenresViewHolder).bind(getItem(position))
                (holder as GenresViewHolder).getLastPos()
                (holder as GenresViewHolder).selectedOption(selectedPos, position)
                holder.onGenreItemClick = onGenreItemClick
            }

            HEADER_FILMS_TYPE -> {
                (holder as HeaderFilmsViewHolder).bind()
            }

            FILM_TYPE -> {
                (holder as FilmTypeViewHolder).bind()
                if (holder is FilmTypeViewHolder) {
                    val recyclerView: RecyclerView = holder.recyclerView
                    val filmAdapter = FilmsAdapter(onFilmClickListener)
                    filmAdapter.submitList(filmList.sortedBy { it.localized_name })
                    filmAdapter.data = filmList.sortedBy { it.localized_name }
                    recyclerView.adapter = filmAdapter
                }
            }
        }
    }

    override fun getItemCount(): Int {

        if (data.isEmpty()) {
            return 0
        }
        val itemsCount = data.size + 3
        return itemsCount;
    }

    override fun getItem(position: Int): Genre = data[position - 1]

    inner class GenresViewHolder(
        private val binding: ItemGenresViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var onGenreItemClick: ((item: Genre, position: Int) -> Unit)? = null

        fun bind(genre: Genre) {
            with(binding) {
                itemGenres.text = genre.name
            }
        }

        init {
            binding.itemGenres.setOnClickListener(this)
        }

        fun selectedOption(selectedPos: Int, position: Int) {

            if (position == prevSelectedPos) {
                defaultBg()
                prevSelectedPos = -1
                Log.w("positions","(position == prevSelectedPos) ${selectedPos}/${position}/${prevSelectedPos}/${sharePref.lastGenresName}/${sharePref.flagClick}")

            }
                if (selectedPos == position ) {
                    if(sharePref.lastGenresName != null) {
                        selectedBg()
                        prevSelectedPos = position
                        return
                    }
                Log.w("positions","(selectedPos == position && sharePref.lastGenresName != null) ${selectedPos}/${prevSelectedPos}/${sharePref.lastGenresName}")
            } else {
                defaultBg()
            }

            Timber.tag("selectedPos")
                .d("selectedOption=" + position + " /" + prevSelectedPos + " /" + bindingAdapterPosition)
        }

        fun defaultBg() {
            binding.itemGenres.background =
                ContextCompat.getDrawable(binding.root.context, R.drawable.item_background)
        }

        fun selectedBg() {
            binding.itemGenres.background = ContextCompat.getDrawable(
                binding.root.context,
                item_background_press
            )
        }

        fun getLastPos(){
            if (sharePref.lastGenresPos >= 0) {
                selectedPos = sharePref.lastGenresPos
                Log.w("positions","getLastPos= ${position}/${prevSelectedPos}/${sharePref.lastGenresPos}/${sharePref.flagClick}/$${sharePref.lastGenresName}")
            }
        }

        override fun onClick(p0: View?) {
            if (selectedPos >= 0) {
                notifyItemChanged(selectedPos)
            }
            selectedPos = bindingAdapterPosition
            Timber.tag("selectedPos").d("onClick=" + selectedPos + " /" + adapterPosition)
            notifyItemChanged(selectedPos)
            onGenreItemClick?.invoke(getItem(position), bindingAdapterPosition)
        }
    }
}