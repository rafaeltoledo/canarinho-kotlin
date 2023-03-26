package net.rafaeltoledo.kanarinho.validator

sealed interface Validator {

  fun isValid(value: String): Boolean

  fun isPartiallyValid(value: String, partialResult: PartialResult): PartialResult

  data class PartialResult(
    val isValid: Boolean = false,
    val isPartiallyValid: Boolean = true,
    val message: String? = null,
  )
}
