package net.rafaeltoledo.kanarinho.formatter

import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.NUMBERS_ONLY

class PhoneFormatter : Formatter {

  private val nineDigitsFormatted = Regex(pattern = "\\((\\d{2})\\)\\s(\\d{5})-(\\d{4})")
  private val nineDigitsUnformatted = Regex(pattern = "(\\d{2})(\\d{5})(\\d{4})")

  private val eightDigitsFormatted = Regex(pattern = "\\((\\d{2})\\)\\s(\\d{4})-(\\d{4})")
  private val eightDigitsUnformatted = Regex(pattern = "(\\d{2})(\\d{4})(\\d{4})")

  private val nineDigitsFormatter = BaseFormatter(
    nineDigitsFormatted, "($1) $2-$3", nineDigitsUnformatted, "$1$2$3"
  )
  private val eightDigitsFormatter = BaseFormatter(
    eightDigitsFormatted, "($1) $2-$3", eightDigitsUnformatted, "$1$2$3"
  )

  override fun format(rawValue: String): String {
    val formatter = if (rawValue.isNineDigits()) nineDigitsFormatter else eightDigitsFormatter
    return formatter.format(rawValue)
  }

  override fun unformat(formattedValue: String): String {
    val formatter = if (formattedValue.isNineDigits()) nineDigitsFormatter else eightDigitsFormatter
    return formatter.unformat(formattedValue)
  }

  override fun isFormatted(value: String): Boolean {
    val formatter = if (value.isNineDigits()) nineDigitsFormatter else eightDigitsFormatter
    return formatter.isFormatted(value)
  }

  override fun canBeFormatted(value: String): Boolean {
    val formatter = if (value.isNineDigits()) nineDigitsFormatter else eightDigitsFormatter
    return formatter.canBeFormatted(value)
  }

  private fun String.isNineDigits() : Boolean {
    return NUMBERS_ONLY.replace(this, "").length > 10
  }
}
