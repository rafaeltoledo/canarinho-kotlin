package net.rafaeltoledo.kanarinho.formatter

import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.CPF_FORMATTED
import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.CPF_UNFORMATTED

class CpfFormatter : Formatter {

  private val delegateFormatter = BaseFormatter(
    CPF_FORMATTED, "$1.$2.$3-$4", CPF_UNFORMATTED, "$1$2$3$4"
  )

  override fun format(rawValue: String): String = delegateFormatter.format(rawValue)

  override fun unformat(formattedValue: String): String = delegateFormatter.unformat(formattedValue)

  override fun isFormatted(value: String): Boolean = delegateFormatter.isFormatted(value)

  override fun canBeFormatted(value: String): Boolean = delegateFormatter.canBeFormatted(value)
}
