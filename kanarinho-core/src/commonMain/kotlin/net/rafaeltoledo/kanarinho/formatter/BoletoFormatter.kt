package net.rafaeltoledo.kanarinho.formatter

class BoletoFormatter : Formatter {

  private val taxFormatted = Regex(pattern = "(\\d{12})\\s(\\d{12})\\s(\\d{12})\\s(\\d{12})")
  private val taxUnformatted = Regex(pattern = "(\\d{12})(\\d{12})(\\d{12})(\\d{12})")

  private val taxFormatter = BaseFormatter(
    taxFormatted, "$1 $2 $3 $4", taxUnformatted, "$1$2$3$4"
  )

  private val ordinaryFormatted = Regex(pattern = "(\\d{5})[.](\\d{5})\\s(\\d{5})[.](\\d{6})\\s(\\d{5})[.](\\d{6})\\s(\\d)\\s(\\d{14})")
  private val ordinaryUnformatted = Regex(pattern = "(\\d{5})(\\d{5})(\\d{5})(\\d{6})(\\d{5})(\\d{6})(\\d)(\\d{14})")

  private val ordinaryFormatter = BaseFormatter(
    ordinaryFormatted, "$1.$2 $3.$4 $5.$6 $7 $8", ordinaryUnformatted,  "$1$2$3$4$5$6$7$8"
  )

  override fun format(rawValue: String): String {
    val formatter = if (rawValue.isTax()) taxFormatter else ordinaryFormatter
    return formatter.format(rawValue)
  }

  override fun unformat(formattedValue: String): String {
    val formatter = if (formattedValue.isTax()) taxFormatter else ordinaryFormatter
    return formatter.unformat(formattedValue)
  }

  override fun isFormatted(value: String): Boolean {
    val formatter = if (value.isTax()) taxFormatter else ordinaryFormatter
    return formatter.isFormatted(value)
  }

  override fun canBeFormatted(value: String): Boolean {
    val formatter = if (value.isTax()) taxFormatter else ordinaryFormatter
    return formatter.canBeFormatted(value)
  }

  private fun String.isTax(): Boolean = this[0] == '8'
}
