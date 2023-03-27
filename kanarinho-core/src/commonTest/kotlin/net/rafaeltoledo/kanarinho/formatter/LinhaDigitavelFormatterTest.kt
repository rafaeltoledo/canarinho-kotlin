package net.rafaeltoledo.kanarinho.formatter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LinhaDigitavelFormatterTest {

  private val formatter = LinhaDigitavelFormatter()

  @Test
  fun `Can format and unformat`() {
    assertEquals("81234567890812345678908123456789081234567890",
      formatter.unformat("812345678901812345678901812345678901812345678901"))

    val original = "23790123016000000005325000456704964680000013580"
    val boleto = formatter.unformat(original)
    val linhaDigitavel = formatter.format(boleto)

    assertEquals(original, linhaDigitavel)
  }

  @Test
  fun `Can check if is formatted`() {
    assertFalse(formatter.isFormatted("81234567890812345678908123456789081234567890"))
    assertTrue(formatter.isFormatted("812345678901 812345678901 812345678901 812345678901"))
    assertTrue(formatter.isFormatted("23790.12301 60000.000053 25000.456704 9 64680000013580"))
  }

  @Test
  fun `Can check if it can format`() {
    assertFalse(formatter.canBeFormatted("8123456789081234567890812345678908123456"))
    assertTrue(formatter.canBeFormatted("23790.1230 60000.00005 25000.45670 9 64680000013580"))
  }
}
