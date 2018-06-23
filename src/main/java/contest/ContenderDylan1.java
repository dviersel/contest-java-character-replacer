package contest;

public class ContenderDylan1 implements Contest.Contender {

  @Override
  public String getDescription() {
    return "(#19) Dylan-1, simple linear impl.";
  }

  @Override
  public String convert(String input) {
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
