package net.rafaeltoledo.kanarinho.formatter

import net.rafaeltoledo.kanarinho.formatter.Formatter.Patterns.NUMBERS_ONLY
import net.rafaeltoledo.kanarinho.validator.BoletoValidator

class LinhaDigitavelFormatter : Formatter {

  private val boletoFormatter = BoletoFormatter()
  private val boletoValidator = BoletoValidator()

  override fun format(rawValue: String): String {
    if (rawValue.length != 44) {
      error("Linha digitável deve conter 44 caracteres. Valor possui ${rawValue.length} caracteres")
    }

    if (rawValue.startsWith("8")) {
      val firstBlock = rawValue.substring(0, 11)
      val secondBlock = rawValue.substring(11, 22)
      val thirdBlock = rawValue.substring(22, 33)
      val fourthBlock = rawValue.substring(33, 44)

      val isMod10 = rawValue[2] == '6' || rawValue[2] == '7'
      val digitFor = if (isMod10) boletoValidator.digitForMod10 else boletoValidator.digitForMod11

      val firstDigit = digitFor.calculate(firstBlock)
      val secondDigit = digitFor.calculate(secondBlock)
      val thirdDigit = digitFor.calculate(thirdBlock)
      val fourthDigit = digitFor.calculate(fourthBlock)

      return "$firstBlock$firstDigit$secondBlock$secondDigit$thirdBlock$thirdDigit$fourthBlock$fourthDigit"
    }

    var firstBlock = rawValue.substring(0, 4)
    var secondBlock = rawValue.substring(4, 19)
    var thirdBlock = rawValue.substring(19, 24)
    var fourthBlock = rawValue.substring(24, 34)
    val fifthBlock = rawValue.substring(34, 44)

    val sortedCode = "$firstBlock$thirdBlock$fourthBlock$fifthBlock$secondBlock"

    firstBlock = sortedCode.substring(0, 9)
    secondBlock = sortedCode.substring(9, 19)
    thirdBlock = sortedCode.substring(19, 29)
    fourthBlock = sortedCode.substring(29)

    val firstDigit = boletoValidator.digitForMod10.calculate(firstBlock)
    val secondDigit = boletoValidator.digitForMod10.calculate(secondBlock)
    val thirdDigit = boletoValidator.digitForMod10.calculate(thirdBlock)

    return "$firstBlock$firstDigit$secondBlock$secondDigit$thirdBlock$thirdDigit$fourthBlock"
  }

  override fun unformat(formattedValue: String): String {
    if (formattedValue.isEmpty()) {
      error("Valor não pode estar vazio.")
    }

    val unformatted = NUMBERS_ONLY.replace(formattedValue, "")

    if (unformatted[0] == '8') {
      if (unformatted.length != 48) {
        error("Valor para boletos que iniciam com 8 deve conter 48 dígitos")
      }

      val firstBlock = unformatted.substring(0, 11)
      val secondBlock = unformatted.substring(12, 23)
      val thirdBlock = unformatted.substring(24, 35)
      val fourthBlock = unformatted.substring(36, 47)

      return "$firstBlock$secondBlock$thirdBlock$fourthBlock"
    }

    if (unformatted.length != 47) {
      error("Valor para boletos deve conter 47 digitos")
    }

    var firstBlock = unformatted.substring(0, 9)
    var secondBlock = unformatted.substring(10, 20)
    var thirdBlock = unformatted.substring(21, 31)
    var fourthBlock = unformatted.substring(32, unformatted.length)

    val sortedBoleto = "$firstBlock$secondBlock$thirdBlock$fourthBlock"

    firstBlock = sortedBoleto.substring(0, 4)
    secondBlock = sortedBoleto.substring(29, 44)
    thirdBlock = sortedBoleto.substring(4, 9)
    fourthBlock = sortedBoleto.substring(9, 19)
    val fifthBlock = sortedBoleto.substring(19, 29)

    return "$firstBlock$secondBlock$thirdBlock$fourthBlock$fifthBlock"
  }

  override fun isFormatted(value: String): Boolean = boletoFormatter.isFormatted(value)

  override fun canBeFormatted(value: String): Boolean {
    return NUMBERS_ONLY.replace(value, "").length == 44
  }
}
