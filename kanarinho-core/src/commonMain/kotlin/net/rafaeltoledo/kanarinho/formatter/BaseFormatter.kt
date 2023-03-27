package net.rafaeltoledo.kanarinho.formatter

class BaseFormatter(
  private val formatted: Regex,
  private val formattedReplacement: String,
  private val unformatted: Regex,
  private val unformattedReplacement: String,
) : Formatter {

  override fun format(rawValue: String): String {
    if (formatted.matches(rawValue)) {
      return rawValue
    }

    return matchAndReplace(unformatted, rawValue, formattedReplacement)
  }

  override fun unformat(formattedValue: String): String {
    if (unformatted.matches(formattedValue)) {
      return formattedValue
    }

    return matchAndReplace(formatted, formattedValue, unformattedReplacement)
  }

  override fun isFormatted(value: String): Boolean {
    return formatted.matches(value)
  }

  override fun canBeFormatted(value: String): Boolean {
    return unformatted.matches(value)
  }

  private fun matchAndReplace(regex: Regex, value: String, replacement: String): String {
    if (!regex.matches(value)) {
      error("Valor não está formatado propriamente.")
    }

    return regex.replace(value, replacement)
  }
}
