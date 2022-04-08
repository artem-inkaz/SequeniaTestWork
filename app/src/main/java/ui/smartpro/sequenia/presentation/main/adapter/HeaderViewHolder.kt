package ui.smartpro.sequenia.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ui.smartpro.sequenia.R
import ui.smartpro.sequenia.databinding.ViewHolderHeaderGenresBinding

class HeaderViewHolder(
    private val binding: ViewHolderHeaderGenresBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ViewHolderHeaderGenresBinding.inflate(inflater, parent, false)
            return HeaderViewHolder(binding)
        }
    }

    fun bind() {
        with(binding) {
            root.apply {
                headerGenresVh.text = resources.getString(R.string.tools_title_header_genres)
            }
        }
    }
}