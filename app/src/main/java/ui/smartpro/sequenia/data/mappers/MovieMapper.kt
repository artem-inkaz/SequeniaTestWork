package ui.smartpro.sequenia.data.mappers

import ui.smartpro.sequenia.data.dto.Genre
import ui.smartpro.sequenia.data.model.Movie
import ui.smartpro.sequenia.data.response.Film
import ui.smartpro.sequenia.data.response.Response
import ui.smartpro.sequenia.extensions.convertToSet

object MovieMapper {

    // получаем список фильмов
    fun convertToMovies(dto: Response): List<Movie> {
        return dto.films.map { convertToMovies(it) }
    }

    private fun convertToMovies(dto: Film): Movie {
        return Movie(
            description = dto.description,
//            genres = dto.genres,
            id = dto.id,
            image_url = dto.image_url,
            localized_name = dto.localized_name,
            name = dto.name,
            rating = dto.rating,
            year = dto.year,
        )
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


    fun getFilmsByGenres(genre: String,film: List<Film>):HashMap<String,List<Film>> {
        val hm = HashMap<String,List<Film>>()
        val filteredList = film.filter {
            it.genres.contains(genre)
        }
        hm.put(genre,filteredList)
        return hm
    }
}