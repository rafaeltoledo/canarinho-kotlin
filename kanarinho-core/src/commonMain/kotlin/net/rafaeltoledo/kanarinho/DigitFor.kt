package net.rafaeltoledo.kanarinho

class DigitFor(
  replacementList: List<ReplacementOptions> = emptyList(),
  multipliersRange: IntRange = IntRange(2, 9),
  multipliers: List<Int>? = null,
  private val complimentaryMod: Boolean = false,
  private val mod: Int = 11,
  private val shouldSumIndividually: Boolean = false,
) {

  private val multipliers = multipliers ?: multipliersRange.toList()
  private val replacements = replacementList.map { options ->
    options.candidates.map { it to options.replacement }
  }.flatten().toMap()

  fun calculate(snippet: String): String {
    val numbers = snippet.map { it.digitToInt() }.reversed()

    var sum = 0
    var currentMultiplier = 0

    numbers.forEach {
      val multiplier = multipliers[currentMultiplier]
      val total = it * multiplier
      sum += if (shouldSumIndividually) total.sumDigits() else total
      currentMultiplier = currentMultiplier.next()
    }

    var result = sum % mod
    if (complimentaryMod) {
      result = mod - result
    }

    return replacements[result] ?: result.toString()
  }

  private fun Int.sumDigits(): Int = (this / 10) + (this % 10)

  private fun Int.next(): Int {
    return if (this + 1 == multipliers.size) 0 else this + 1
  }

  data class ReplacementOptions(
    val candidates: List<Int>,
    val replacement: String,
  )
}


