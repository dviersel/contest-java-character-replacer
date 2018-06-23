package contest

import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

class ContenderDylan5 : Contest.Contender {

  override fun getDescription(): String {
    return "(#24) Dylan-5, Kotlin co-routines (recursive)"
  }

  override fun convert(input: String): String {
    val chars = input.toCharArray()

val job =       launch {
        convert2(chars, 0, chars.size)
      }

    runBlocking {
      job.join()
    }
    return chars.joinToString("")
  }

  private val LIMIT = 10000

  fun convert2(chars: CharArray, lo: Int, hi: Int) {
    if (hi - lo < LIMIT) {
      launch { complement(chars, lo, hi) }
    } else {
      val split = (hi - lo) / 2
      launch { convert2(chars, lo, lo + split) }
      launch { convert2(chars, lo + split, hi) }
    }
  }

  fun complement(chars: CharArray, start: Int, end: Int) {
    (start until end).forEach { idx ->
      when (chars[idx]) {
        'A' -> chars[idx] = 'T'
        'T' -> chars[idx] = 'A'
        'G' -> chars[idx] = 'C'
        'C' -> chars[idx] = 'G'
        else -> {
          throw IllegalArgumentException()
        }
      }
    }
  }
}