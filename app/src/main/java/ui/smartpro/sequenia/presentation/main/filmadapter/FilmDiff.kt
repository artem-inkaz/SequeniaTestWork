package ui.smartpro.sequenia.presentation.main.filmadapter

import androidx.recyclerview.widget.DiffUtil
import ui.smartpro.sequenia.data.response.Film

object FilmDiff : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}