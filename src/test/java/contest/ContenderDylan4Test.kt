package contest

import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class ContenderDylan4Test {

  @Test
  fun testComplement() {
    val c = ContenderDylan4()
    val input = "ACGT".toCharArray()
    c.complement(input, 0, 4)
    assertEquals(input.joinToString(""), "TGCA")
  }
}