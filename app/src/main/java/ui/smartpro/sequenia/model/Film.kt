package ui.smartpro.sequenia.model

data class Film(
    val description: String,
    val genres: List<String>,
    val id: Int,
    val image_url: String,
    val localized_name: String,
    val name: String,
    val rating: Double,
    val year: Int
)