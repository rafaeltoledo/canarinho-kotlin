package net.rafaeltoledo.kanarinho.validator

import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.NUMBERS_ONLY

class PhoneValidator : Validator {
  override fun isValid(value: String): Boolean {
    if (value.length < 10) {
      return false
    }

    val unformatted = NUMBERS_ONLY.replace(value, "")

    return unformatted.length == 10 || unformatted.length == 11
  }

  override fun isPartiallyValid(value: String, partialResult: Validator.PartialResult): Validator.PartialResult {
    val unformatted = NUMBERS_ONLY.replace(value, "")

    if (!isValid(unformatted)) {
      return partialResult.copy(
        isPartiallyValid = unformatted.length < 11,
        message = "Telefone inválido",
        isValid = false,
      )
    }

    return partialResult.copy(
      isPartiallyValid = true,
      isValid = true,
    )
  }
}
