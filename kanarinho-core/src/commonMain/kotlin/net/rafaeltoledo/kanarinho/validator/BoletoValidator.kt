package net.rafaeltoledo.kanarinho.validator

import net.rafaeltoledo.kanarinho.DigitFor
import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.NUMBERS_ONLY

class BoletoValidator : Validator {

  private val digitForMod10 = DigitFor(
    mod = 10,
    multipliers = listOf(2, 1),
    shouldSumIndividually = true,
    complimentaryMod = true,
    replacementList = listOf(
      DigitFor.ReplacementOptions(
        candidates = listOf(10),
        replacement = "0",
      )
    )
  )

  private val digitForMod11 = DigitFor(
    complimentaryMod = true,
    replacementList = listOf(
      DigitFor.ReplacementOptions(
        candidates = listOf(10, 11),
        replacement = "0",
      )
    )
  )

  override fun isValid(value: String): Boolean {
    val unformatted = NUMBERS_ONLY.replace(value, "")
    return isPartiallyValid(unformatted, Validator.PartialResult()).isValid
  }

  override fun isPartiallyValid(value: String, partialResult: Validator.PartialResult): Validator.PartialResult {
    val unformattedValue = CLEAN_REGEX.replace(value, "")

    if (!NUMBERS_ONLY_REGEX.matches(unformattedValue)) {
      error("Apenas números, '.' e espaços são válidos")
    }

    val mutablePartialResult = partialResult.toMutable()
    mutablePartialResult.isValid = false

    if (unformattedValue.isEmpty()) {
      return mutablePartialResult.apply {
        isPartiallyValid = true
        message = null
      }.toPartialResult()
    }

    return if (unformattedValue.isTax()) {
      taxValidation(unformattedValue, mutablePartialResult)
    } else {
      commonValidation(unformattedValue, mutablePartialResult)
    }
  }

  private fun commonValidation(
    value: String,
    partialResult: MutablePartialResult,
  ): Validator.PartialResult {
    if (!blockValidation(value, partialResult, digitForMod10, 10, 0, "Primeiro")) {
      return partialResult.toPartialResult()
    }

    if (!blockValidation(value, partialResult, digitForMod10, 21, 10, "Segundo")) {
      return partialResult.toPartialResult()
    }

    if (!blockValidation(value, partialResult, digitForMod10, 32, 21, "Terceiro")) {
      return partialResult.toPartialResult()
    }

    if (value.length < 47) {
      return partialResult.toPartialResult()
    }

    return partialResult.apply {
      isPartiallyValid = true
      isValid = true
    }.toPartialResult()
  }

  private fun taxValidation(
    value: String,
    partialResult: MutablePartialResult,
  ): Validator.PartialResult {
    if (value.length < 3) {
      partialResult.isPartiallyValid = true
    }

    val isMod10 = value[2] == '6' || value[2] == '7'
    val digitFor = if (isMod10) digitForMod10 else digitForMod11

    if (!blockValidation(value, partialResult, digitFor, 12, 0, "Primeiro")) {
      return partialResult.toPartialResult()
    }

    if (!blockValidation(value, partialResult, digitFor, 24, 12, "Segundo")) {
      return partialResult.toPartialResult()
    }

    if (!blockValidation(value, partialResult, digitFor, 36, 24, "Terceiro")) {
      return partialResult.toPartialResult()
    }

    if (!blockValidation(value, partialResult, digitFor, 48, 36, "Quarto")) {
      return partialResult.toPartialResult()
    }

    return partialResult.apply {
      isPartiallyValid = true
      isValid = true
    }.toPartialResult()
  }

  private fun blockValidation(
    value: String,
    partialResult: MutablePartialResult,
    mod: DigitFor,
    minSize: Int,
    st: Int,
    message: String,
  ): Boolean {
    if (value.length < minSize) {
      partialResult.isPartiallyValid = true
      return false
    }

    val end = minSize - 1
    val digit = mod.calculate(value.subSequence(st, end).toString())[0]

    if (digit != value[end]) {
      return partialResult.apply {
        this.message = "$message bloco inválido"
        isPartiallyValid = false
      }.isPartiallyValid
    }

    return true
  }

  private fun Validator.PartialResult.toMutable(): MutablePartialResult {
    return MutablePartialResult(isValid, isPartiallyValid, message)
  }

  private data class MutablePartialResult(
    var isValid: Boolean = false,
    var isPartiallyValid: Boolean = true,
    var message: String? = null,
  ) {
    fun toPartialResult() = Validator.PartialResult(isValid, isPartiallyValid, message)
  }

  private companion object {
    val CLEAN_REGEX = Regex(pattern = "[\\s.]")
    val NUMBERS_ONLY_REGEX = Regex(pattern = "\\d*")
  }
}

private fun String.isTax(): Boolean = this[0] == '8'
