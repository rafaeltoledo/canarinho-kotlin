package net.rafaeltoledo.kanarinho.validator

import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.NUMBERS_ONLY

class CpfCnpjValidator : Validator {

  override fun isValid(value: String): Boolean {
    if (value.length != 11 && value.length != 14) {
      return false
    }

    if (value.isCpf()) {
      return CpfValidator().isValid(value)
    }

    return CnpjValidator().isValid(value)
  }

  override fun isPartiallyValid(value: String, partialResult: Validator.PartialResult): Validator.PartialResult {
    if (value.isCpf()) {
      return CpfValidator().isPartiallyValid(value, partialResult)
    }

    return CnpjValidator().isPartiallyValid(value, partialResult)
  }

  private fun String.isCpf(): Boolean {
    val unformatted = NUMBERS_ONLY.replace(this, "")
    return unformatted.length < 12
  }
}

