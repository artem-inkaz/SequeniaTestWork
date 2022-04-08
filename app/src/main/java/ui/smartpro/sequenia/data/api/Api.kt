package ui.smartpro.sequenia.data.api

import retrofit2.Call
import retrofit2.http.GET
import ui.smartpro.sequenia.data.response.Response

interface Api {

    /**
     * https://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json
     */

    @GET("sequeniatesttask/films.json")
    fun getFilms(): Call<Response>
}