package ui.smartpro.sequenia.data.model

data class Movie(
    val description: String?,
    val id: Int,
    val image_url: String?,
    val localized_name: String,
    val name: String,
    val rating: Double,
    val year: Int
)
