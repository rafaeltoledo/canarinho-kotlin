package net.rafaeltoledo.kanarinho.formatter

import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.NUMBERS_ONLY

class CpfCnpjFormatter : Formatter {

  private val cpfFormatter = CpfFormatter()
  private val cnpjFormatter = CnpjFormatter()

  override fun format(rawValue: String): String {
    val formatter: Formatter = if (rawValue.isCpf()) cpfFormatter else cnpjFormatter
    return formatter.format(rawValue)
  }

  override fun unformat(formattedValue: String): String {
    val formatter: Formatter = if (formattedValue.isCpf()) cpfFormatter else cnpjFormatter
    return formatter.unformat(formattedValue)
  }

  override fun isFormatted(value: String): Boolean {
    val formatter: Formatter = if (value.isCpf()) cpfFormatter else cnpjFormatter
    return formatter.isFormatted(value)
  }

  override fun canBeFormatted(value: String): Boolean {
    val formatter: Formatter = if (value.isCpf()) cpfFormatter else cnpjFormatter
    return formatter.canBeFormatted(value)
  }

  private fun String.isCpf(): Boolean {
    return NUMBERS_ONLY.replace(this, "").length < 12
  }
}
