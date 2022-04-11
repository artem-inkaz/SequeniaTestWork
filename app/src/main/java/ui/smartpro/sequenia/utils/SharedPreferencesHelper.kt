package ui.smartpro.sequenia.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.smartpro.sequenia.data.response.Film

class SharedPreferencesHelper(ctx: Context) {
    private val prefs =
        ctx.getSharedPreferences(CommonConstants.APP_PREFERENCES, Context.MODE_PRIVATE)

    companion object {
        private const val LAST_GENRES_POS = "LAST_GENRES_POS"
        private const val LAST_GENRES_NAME = "LAST_GENRES_NAME"
        private const val LAST_FILM_LIST = "LAST_FILM_LIST"
        private const val FLAG_CLICK = "FLAG_CLICK"
        private const val FIRST_OPEN = "FIRST_OPEN"
        private const val SECOND_OPEN = "SECOND_OPEN"
    }

    var firstOpen: Int = 0
        set(value) {
            field = value
            prefs.edit().putInt(FIRST_OPEN, value).apply()
        }
        get() = prefs.getInt(FIRST_OPEN, 0)

    var secondOpen: Int = 0
        set(value) {
            field = value
            prefs.edit().putInt(SECOND_OPEN, value).apply()
        }
        get() = prefs.getInt(SECOND_OPEN, 0)

    var lastGenresPos: Int = -1
        set(value) {
            field = value
            prefs.edit().putInt(LAST_GENRES_POS, value).apply()
        }
        get() = prefs.getInt(LAST_GENRES_POS, -1)

    var lastGenresName: String? = null
        set(value) {
            field = value
            prefs.edit().putString(LAST_GENRES_NAME, value).apply()

        }
        get() = prefs.getString(LAST_GENRES_NAME, null)

    var flagClick: Boolean = false
        set(value) {
            field = value
            prefs.edit().putBoolean(FLAG_CLICK, value).apply()
        }
        get() = prefs.getBoolean(FLAG_CLICK, false)

    var lastFilmList: List<Film> = listOf()
        set(value) {
            val gson = Gson()
            val json = gson.toJson(value)
            field = value
            prefs.edit().putString(LAST_FILM_LIST, json).apply()
        }
        get() = Gson().fromJson<Any>(prefs.getString(LAST_FILM_LIST, null),
            object : TypeToken<ArrayList<Film?>?>() {}.type) as List<Film> ?: listOf()

}