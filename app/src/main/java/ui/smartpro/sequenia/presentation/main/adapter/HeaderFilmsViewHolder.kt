package ui.smartpro.sequenia.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ui.smartpro.sequenia.R
import ui.smartpro.sequenia.databinding.ViewHolderHeaderFilmsBinding

class HeaderFilmsViewHolder(
    private val binding: ViewHolderHeaderFilmsBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): HeaderFilmsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ViewHolderHeaderFilmsBinding.inflate(inflater, parent, false)
            return HeaderFilmsViewHolder(binding)
        }
    }

    fun bind() {
        with(binding) {
            root.apply {
                headerMovieTv.text = context.getString(R.string.header_movie_list_container)
            }
        }
    }
}