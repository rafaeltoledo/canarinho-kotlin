package net.rafaeltoledo.kanarinho.formatter

import kotlin.test.*

class CepFormatterTest {

  private val formatter = CepFormatter()

  @Test
  fun `Can format`() {
    assertEquals("00000-000", formatter.format("00000-000"))
    assertEquals("00000-000", formatter.format("00000000"))

    assertEquals("12345-123", formatter.format("12345-123"))
    assertEquals("12345-678", formatter.format("12345678"))

    assertFails(message = "Valor não está formatado propriamente.") { formatter.format("") }
    assertFails(message = "Valor não está formatado propriamente.") { formatter.format("123123") }
  }

  @Test
  fun `Can unformat`() {
    assertEquals("00000000", formatter.unformat("00000-000"))
    assertEquals("00000000", formatter.unformat("00000000"))

    assertEquals("12345123", formatter.unformat("12345-123"))
    assertEquals("12345678", formatter.unformat("12345678"))

    assertFails(message = "Valor não está formatado propriamente.") { formatter.unformat("") }
    assertFails(message = "Valor não está formatado propriamente.") { formatter.unformat("123123") }
  }

  @Test
  fun `Can check if value is formatted`() {
    assertTrue(formatter.isFormatted("12345-678"))
    assertFalse(formatter.isFormatted("12345678"))
    assertTrue(formatter.isFormatted("00000-000"))
    assertFalse(formatter.isFormatted("12345-67"))

    assertFalse(formatter.isFormatted("047486"))
    assertFalse(formatter.isFormatted(""))
  }

  @Test
  fun `Can check if value can be formatted`() {
    assertFalse(formatter.canBeFormatted("12345-678"))
    assertTrue(formatter.canBeFormatted("12345678"))
    assertFalse(formatter.canBeFormatted("020"))
    assertFalse(formatter.canBeFormatted(""))
  }
}
