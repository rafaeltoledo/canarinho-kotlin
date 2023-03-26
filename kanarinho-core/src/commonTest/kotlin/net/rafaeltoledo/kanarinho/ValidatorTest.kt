package net.rafaeltoledo.kanarinho

import net.rafaeltoledo.kanarinho.validator.*
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidatorTest {

  @Test
  fun `Can validate CPFs`() {
    val validator = CpfValidator()

    // Valid formatted CPFs
    assertTrue(validator.isValid("545.586.262-66"))
    assertTrue(validator.isValid("655.274.921-02"))
    assertTrue(validator.isValid("020.724.833-87"))
    assertTrue(validator.isValid("188.527.045-31"))
    assertTrue(validator.isValid("047.486.777-32"))

    // Valid unformatted CPFs
    assertTrue(validator.isValid("54558626266"))
    assertTrue(validator.isValid("65527492102"))
    assertTrue(validator.isValid("02072483387"))
    assertTrue(validator.isValid("18852704531"))
    assertTrue(validator.isValid("04748677732"))

    // Invalid formatted CPFs
    assertFalse(validator.isValid("545.111.262-66"))
    assertFalse(validator.isValid("655.111.921-02"))
    assertFalse(validator.isValid("020.111.833-87"))
    assertFalse(validator.isValid("188.111.045-31"))
    assertFalse(validator.isValid("047.111.777-32"))

    // Invalid unformatted CPFs
    assertFalse(validator.isValid("54111626266"))
    assertFalse(validator.isValid("65111492102"))
    assertFalse(validator.isValid("02111483387"))
    assertFalse(validator.isValid("18111704531"))
    assertFalse(validator.isValid("04111677732"))
  }

  @Test
  fun `Can validate CNPJs`() {
    val validator = CnpjValidator()

    // Valid formatted CPFs
    assertTrue(validator.isValid("50.713.534/0001-33"))
    assertTrue(validator.isValid("77.135.038/0001-04"))
    assertTrue(validator.isValid("58.613.246/0001-19"))

    // Valid unformatted CPFs
    assertTrue(validator.isValid("50713534000133"))
    assertTrue(validator.isValid("77135038000104"))
    assertTrue(validator.isValid("58613246000119"))

    // Invalid formatted CPFs
    assertFalse(validator.isValid("50.713.111/0001-33"))
    assertFalse(validator.isValid("77.135.111/0001-04"))

    // Invalid unformatted CPFs
    assertFalse(validator.isValid("50713531110133"))
    assertFalse(validator.isValid("77135031110104"))
  }

  @Test
  fun `Can validate ordinary boletos`() {
    val validator = BoletoValidator()

    // Boleto from Bradesco
    assertTrue(validator.isValid("23790.12301 60000.000053 25000.456704 9 64680000013580"))
    // Boleto from Bradesco (unformatted)
    assertTrue(validator.isValid("23790123016000000005325000456704964680000013580"))
    // Boleto from Banco do Brasil
    assertTrue(validator.isValid("00199.38414 90480.002550 84666.970219 4 64290000007726"))
    // Boleto from Itaú (unformatted)
    assertTrue(validator.isValid("34191750090000159091820521070001664890002370000"))
    // Boleto from Itaú
    assertTrue(validator.isValid("34191.75009 00001.590918 20521.070001 6 64890002370000"))
    // Boleto from Banestes
    assertTrue(validator.isValid("02190.00015 05000.000017 23452.021696 2 25230000034000"))
    // Boleto from Caixa
    assertTrue(validator.isValid("10491.44338 55119.000002 00000.000141 3 25230000093423"))
    // Boleto from Sudameris
    assertTrue(validator.isValid("34790.12001 12345.695006 10502.000002 5 25230000034000"))
  }

  @Test
  fun `Can validate tax boleto - other than fee`() {
    val validator = BoletoValidator()
    assertTrue(validator.isValid("848600000015 523301622010 506101307129 620012111220"))
  }

  @Test
  fun `Can validate tax boleto - fee`() {
    val validator = BoletoValidator()
    assertTrue(validator.isValid("836600000019 078800481000 998854924516 001265611135"))
    assertTrue(validator.isValid("826100000007 265400971429 620232390612 725103150621"))
  }

  @Test
  fun `Can validate phone numbers`() {
    val validator = PhoneValidator()
    assertTrue(validator.isValid("1112345678"))
    assertTrue(validator.isValid("11123456789"))
    assertTrue(validator.isValid("(11) 12345-6789"))
    assertFalse(validator.isValid("111234567890"))
    assertFalse(validator.isValid("1112345"))
  }

  @Test
  fun `Can validate CEPs`() {
    val validator = CepValidator()
    assertTrue(validator.isValid("12345678"))
    assertTrue(validator.isValid("12345-678"))
    assertFalse(validator.isValid("12345-67"))
    assertFalse(validator.isValid("1234-678"))
    assertFalse(validator.isValid(""))
  }
}
