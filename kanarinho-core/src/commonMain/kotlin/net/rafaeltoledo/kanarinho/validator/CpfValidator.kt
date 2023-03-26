package net.rafaeltoledo.kanarinho.validator

import net.rafaeltoledo.kanarinho.DigitFor
import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.NUMBERS_ONLY

class CpfValidator : Validator {

  private val expectedUnformattedSize = 11

  private val digitForCpf = DigitFor(
    multipliersRange = IntRange(2, 11),
    complimentaryMod = true,
    replacementList = listOf(
      DigitFor.ReplacementOptions(
        candidates = listOf(10, 11),
        replacement = "0",
      )
    )
  )

  override fun isValid(value: String): Boolean {
    if (value.length < expectedUnformattedSize) {
      return false
    }

    val unformatted = NUMBERS_ONLY.replace(value, "")

    if (unformatted.length != expectedUnformattedSize) {
      return false
    }

    if (unformatted.isBlockListed()) {
      return false
    }

    val cpfWithoutDigit = unformatted.substring(0, unformatted.length - 2)
    val digits = unformatted.substring(unformatted.length - 2)

    val firstDigit = digitForCpf.calculate(cpfWithoutDigit)
    val secondDigit = digitForCpf.calculate("$cpfWithoutDigit$firstDigit")

    return "$firstDigit$secondDigit" == digits
  }

  override fun isPartiallyValid(value: String, partialResult: Validator.PartialResult): Validator.PartialResult {
    val unformatted = NUMBERS_ONLY.replace(value, "")

    if (!isValid(unformatted)) {
      return partialResult
        .copy(
          isPartiallyValid = unformatted.length < expectedUnformattedSize,
          message = "CPF inválido", // TODO improve messages
          isValid = false,
        )
    }

    return partialResult
      .copy(isPartiallyValid = true, isValid = true)
  }

  private fun String.isBlockListed(): Boolean {
    val allNumbersAreTheSame = Regex(pattern = "^(.)\\1*$").matches(this)
    return allNumbersAreTheSame || this == "12345678909"
  }
}
