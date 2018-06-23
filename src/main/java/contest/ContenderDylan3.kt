package contest

class ContenderDylan3 : Contest.Contender {
  override fun getDescription(): String {
    return "(#21) Dylan-3, Kotlin replace+toUpper"
  }

  override fun convert(input: String?): String {
    return input!!
        .replace('A','t')
        .replace('T', 'A')
        .replace('C', 'g')
        .replace('G', 'C')
        .toUpperCase()
  }
}