package ui.smartpro.sequenia.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ui.smartpro.sequenia.R
import ui.smartpro.sequenia.databinding.ItemMovieListContainerRvBinding

class FilmTypeViewHolder(
    private val binding: ItemMovieListContainerRvBinding
) : RecyclerView.ViewHolder(binding.root) {

    var recyclerView: RecyclerView = binding.movieListRv

    companion object {
        fun create(parent: ViewGroup): FilmTypeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMovieListContainerRvBinding.inflate(inflater, parent, false)
            return FilmTypeViewHolder(binding)
        }
    }

    fun bind() = with(binding) {
        headerMovieListTv.text = root.context.getString(R.string.header_movie_list_container)
        headerMovieListTv.visibility = View.GONE
        recyclerView = binding.movieListRv
        recyclerView.layoutManager =
            GridLayoutManager(root.context, 2, RecyclerView.VERTICAL, false)
    }
}