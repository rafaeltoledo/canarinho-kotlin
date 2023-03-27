package net.rafaeltoledo.kanarinho.formatter

import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.CEP_FORMATTED
import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.CEP_UNFORMATTED

class CepFormatter : Formatter {

  private val delegateFormatter = BaseFormatter(
    CEP_FORMATTED, "$1-$2", CEP_UNFORMATTED, "$1$2"
  )

  override fun format(rawValue: String): String = delegateFormatter.format(rawValue)

  override fun unformat(formattedValue: String): String = delegateFormatter.unformat(formattedValue)

  override fun isFormatted(value: String): Boolean = delegateFormatter.isFormatted(value)

  override fun canBeFormatted(value: String): Boolean = delegateFormatter.canBeFormatted(value)
}
