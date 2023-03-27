package net.rafaeltoledo.kanarinho.formatter

sealed interface Formatter {

  fun format(rawValue: String): String

  fun unformat(formattedValue: String): String

  fun isFormatted(value: String): Boolean

  fun canBeFormatted(value: String): Boolean

  object Patterns {
    val NUMBERS_ONLY = Regex(pattern = "[^0-9]")

    val CEP_FORMATTED = Regex(pattern = "(\\d{5})-(\\d{3})")
    val CEP_UNFORMATTED = Regex(pattern = "(\\d{5})(\\d{3})")

    val CNPJ_FORMATTED = Regex(pattern = "(\\d{2})[.](\\d{3})[.](\\d{3})/(\\d{4})-(\\d{2})")
    val CNPJ_UNFORMATTED = Regex(pattern = "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})")

    val CPF_FORMATTED = Regex(pattern = "(\\d{3})[.](\\d{3})[.](\\d{3})-(\\d{2})")
    val CPF_UNFORMATTED = Regex(pattern = "(\\d{3})(\\d{3})(\\d{3})(\\d{2})")
  }
}
