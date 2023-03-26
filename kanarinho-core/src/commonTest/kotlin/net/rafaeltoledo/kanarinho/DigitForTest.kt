package net.rafaeltoledo.kanarinho

import kotlin.test.Test
import kotlin.test.assertEquals

class DigitForTest {

  @Test
  fun `Digit generation for boleto`() {
    val entries = mapOf(
      "3999100100001200000351202000003910476618602" to "3",
      "2379316800000001002949060000000000300065800" to "6",
      "0019386000000040000000001207113000900020618" to "5",
      "0000039104766" to "3",
    )

    val digitFor = DigitFor(
      multipliersRange = IntRange(2, 9),
      complimentaryMod = true,
      mod = 11,
    )
    entries.forEach {
      assertEquals(it.value, digitFor.calculate(it.key))
    }
  }

  @Test
  fun `Digit generation for boleto - special cases`() {
    val entries = mapOf(
      "3999100100001200000351202000003911476618611" to "1",
      "2379316800000001002949060000000100300065885" to "1",
    )

    val digitFor = DigitFor(
      multipliersRange = IntRange(2, 9),
      complimentaryMod = true,
      mod = 11,
      replacementList = listOf(
        DigitFor.ReplacementOptions(
          candidates = listOf(0, 10, 11),
          replacement = "1"
        )
      )
    )
    entries.forEach {
      assertEquals(it.value, digitFor.calculate(it.key))
    }
  }

  @Test
  fun `Digit generation with mod 11 for past interval`() {
    val digitFor = DigitFor(
      multipliers = listOf(9, 8, 7, 6, 5, 4, 3, 2),
      mod = 11,
    )

    assertEquals("1", digitFor.calculate("05009401448"))
  }

  @Test
  fun `Digit generation for RG from Sao Paulo`() {
    assertEquals(
      "1", DigitFor(
        multipliersRange = IntRange(2, 9),
        mod = 11,
      ).calculate("36422911")
    )
    assertEquals(
      "X", DigitFor(
        multipliersRange = IntRange(2, 9),
        mod = 11,
        replacementList = listOf(
          DigitFor.ReplacementOptions(
            replacement = "X",
            candidates = listOf(10),
          ),
        )
      ).calculate("42105900")
    )
  }
}
