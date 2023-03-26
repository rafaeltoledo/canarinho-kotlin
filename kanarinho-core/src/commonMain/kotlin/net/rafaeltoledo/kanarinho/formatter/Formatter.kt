package net.rafaeltoledo.kanarinho.formatter

sealed interface Formatter {

  fun format(rawValue: String): String

  fun unformat(formattedValue: String): String

  fun isFormatted(value: String): Boolean

  fun canBeFormatted(value: String): Boolean

  object Patterns {
    val NUMBERS_ONLY = Regex(pattern = "[^0-9]")
  }
}
