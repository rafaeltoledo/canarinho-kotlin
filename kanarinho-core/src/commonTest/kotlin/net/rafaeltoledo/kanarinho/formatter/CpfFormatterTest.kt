package net.rafaeltoledo.kanarinho.formatter

import kotlin.test.*

class CpfFormatterTest {

  private val formatter = CpfFormatter()

  @Test
  fun `Can format CPFs`() {
    assertEquals("545.586.262-66", formatter.format("545.586.262-66"))
    assertEquals("545.586.262-66", formatter.format("54558626266"))

    assertEquals("020.724.833-87", formatter.format("020.724.833-87"))
    assertEquals("020.724.833-87", formatter.format("02072483387"))

    assertEquals("188.527.045-31", formatter.format("188.527.045-31"))
    assertEquals("188.527.045-31", formatter.format("18852704531"))

    assertEquals("047.486.777-32", formatter.format("047.486.777-32"))
    assertEquals("047.486.777-32", formatter.format("04748677732"))

    assertFails(message = "Valor não está formatado propriamente.") { formatter.format("") }
    assertFails(message = "Valor não está formatado propriamente.") { formatter.format("123123") }
    assertFails(message = "Valor não está formatado propriamente.") { formatter.format("123.123.123    -12") }
    assertFails(message = "Valor não está formatado propriamente.") { formatter.format("       047.486.777-32      ") }
  }

  @Test
  fun `Can unformat CPFs`() {
    assertEquals("54558626266", formatter.unformat("545.586.262-66"))
    assertEquals("54558626266", formatter.unformat("54558626266"))

    assertEquals("02072483387", formatter.unformat("020.724.833-87"))
    assertEquals("02072483387", formatter.unformat("02072483387"))

    assertEquals("18852704531", formatter.unformat("188.527.045-31"))
    assertEquals("18852704531", formatter.unformat("18852704531"))

    assertEquals("04748677732", formatter.unformat("047.486.777-32"))
    assertEquals("04748677732", formatter.unformat("04748677732"))

    assertFails(message = "Valor não está formatado propriamente.") { formatter.unformat("") }
    assertFails(message = "Valor não está formatado propriamente.") { formatter.unformat("123123") }
    assertFails(message = "Valor não está formatado propriamente.") { formatter.unformat("123.123.123    -12") }
    assertFails(message = "Valor não está formatado propriamente.") { formatter.unformat("       047.486.777-32      ") }
  }

  @Test
  fun `Can check if CPF is formatted`() {
    assertTrue(formatter.isFormatted("545.586.262-66"))
    assertFalse(formatter.isFormatted("54558626266"))

    assertTrue(formatter.isFormatted("020.724.833-87"))
    assertFalse(formatter.isFormatted("02072483387"))

    assertTrue(formatter.isFormatted("188.527.045-31"))
    assertFalse(formatter.isFormatted("18852704531"))

    assertTrue(formatter.isFormatted("047.486.777-32"))
    assertFalse(formatter.isFormatted("04748677732"))

    assertFalse(formatter.isFormatted("047486"))
    assertFalse(formatter.isFormatted(""))
  }

  @Test
  fun `Can check if value can be formatted`() {
    assertFalse(formatter.canBeFormatted("545.586.262-66"))
    assertTrue(formatter.canBeFormatted("54558626266"))
    assertFalse(formatter.canBeFormatted("020"))
    assertFalse(formatter.canBeFormatted(""))
  }
}
