package ui.smartpro.sequenia.data.model

class CountersModel(private val counters: MutableList<Int> = mutableListOf(0)) {

    fun incrementCounter(counterId: Int) = ++counters[counterId]
}