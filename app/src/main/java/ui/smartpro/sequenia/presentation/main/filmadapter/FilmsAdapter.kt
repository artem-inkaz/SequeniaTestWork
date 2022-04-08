package ui.smartpro.sequenia.presentation.main.filmadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ui.smartpro.sequenia.data.response.Film

class FilmsAdapter(
    var onFilmClickListener: OnFilmClickListener
) : ListAdapter<Film, FilmsViewHolder>(FilmDiff) {

    var data: List<Film> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder =
        FilmsViewHolder.create(parent)

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onFilmClickListener.onItemFilmClickListener(getItem(position))
        }
    }
}