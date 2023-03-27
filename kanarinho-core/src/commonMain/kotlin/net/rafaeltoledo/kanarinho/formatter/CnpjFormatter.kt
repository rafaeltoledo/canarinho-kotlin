package net.rafaeltoledo.kanarinho.formatter

import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.CNPJ_FORMATTED
import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.CNPJ_UNFORMATTED

class CnpjFormatter : Formatter {

  private val delegateFormatter = BaseFormatter(
    CNPJ_FORMATTED, "$1.$2.$3/$4-$5", CNPJ_UNFORMATTED, "$1$2$3$4$5"
  )

  override fun format(rawValue: String): String = delegateFormatter.format(rawValue)

  override fun unformat(formattedValue: String): String = delegateFormatter.unformat(formattedValue)

  override fun isFormatted(value: String): Boolean = delegateFormatter.isFormatted(value)

  override fun canBeFormatted(value: String): Boolean = delegateFormatter.canBeFormatted(value)
}
