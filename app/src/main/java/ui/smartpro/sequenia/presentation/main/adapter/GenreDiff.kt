package ui.smartpro.sequenia.presentation.main.adapter

import androidx.recyclerview.widget.DiffUtil
import ui.smartpro.sequenia.data.dto.Genre

object GenreDiff : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem == newItem
    }
}