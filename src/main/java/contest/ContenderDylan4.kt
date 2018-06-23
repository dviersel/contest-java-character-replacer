package contest

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

class ContenderDylan4 : Contest.Contender {

  val chunks = Runtime.getRuntime().availableProcessors() * 8

  override fun getDescription(): String {
    return "(#23) Dylan-4, Kotlin co-routines"
  }

  override fun convert(input: String): String {
    val chars = input.toCharArray()
    val size = input.length / chunks
    val jobs = arrayListOf<Job>()
    for (i in 0 until chunks) {
      jobs.add(
          launch { complement(chars, i * size, (i + 1) * size) }
      )
    }
    runBlocking {
      jobs.forEach { job -> job.join() }
    }
    return chars.joinToString("")
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