package ui.smartpro.sequenia.data.mappers

import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.response.Film

object MovieMapper {

    // получаем список жанров коллекция Set
    fun getMapAllGenres(genres: List<Film>): List<Genre> {
        val setGenres: MutableSet<String> = mutableSetOf()
        genres.forEach {
            setGenres.addAll(it.genres)
        }
        setGenres
            .sorted()
            .toSet()
       return listToGenre(setGenres.toList())
    }

    // получаем список жанров в модель Genre из List<String> ф-и convertAllGenresToList или convertAllGenresToList
    fun listToGenre(genre: List<String>): List<Genre> {
        val listGenre = genre.map {
            toGenreObject(it)
        }
        return listGenre
    }

    private fun toGenreObject(dto: String): Genre {
        return Genre(
            name = dto
        )
    }

    fun getFilmsByGenres(genre: String,film: List<Film>):HashMap<String,List<Film>> {
        val hm = HashMap<String,List<Film>>()
        val filteredList = film.filter {
            it.genres.contains(genre)
        }
        hm.put(genre,filteredList)
        return hm
    }
}