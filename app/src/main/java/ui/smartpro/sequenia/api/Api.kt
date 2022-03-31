package ui.smartpro.sequenia.api

import retrofit2.Call
import retrofit2.http.GET
import ui.smartpro.sequenia.model.Response

interface Api {

    /**
     * https://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json
     */

    @GET("sequeniatesttask/films.json")
    suspend fun getFilms(): Call<Response>
}