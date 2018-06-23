package contest;

public class ContenderDylan2 implements Contest.Contender {

  @Override
  public String getDescription() {
    return "(#20) Dylan-1, simple recursive impl.";
  }

  @Override
  public String convert(String input) {

    if (input.length() < 10) {
      return complement(input);
    } else {
      int split = input.length() / 2;
      return convert(input.substring(0, split)) + convert(input.substring(split));
    }
  }

  String complement(String input) {
    char[] charArray = input.toCharArray();
    for (int i = 0; i < input.length(); i++) {
      switch (charArray[i]) {
        case 'T':
          charArray[i] = 'A';
          break;
        case 'A':
          charArray[i] = 'T';
          break;
        case 'C':
          charArray[i] = 'G';
          break;
        case 'G':
          charArray[i] = 'C';
          break;
      }
    }
    return String.valueOf(charArray);
  }
}
