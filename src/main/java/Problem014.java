import java.util.HashMap;

public class Problem014 {
  public static void main(String[] args) {
    HashMap<Long, Long> startToLength = new HashMap<>();
    long limit = 1000000L;
    long longestStart = 1L;
    long longestLength = 1L;
    startToLength.put(longestStart, longestLength);
    for (long nextCandidate = 2L; nextCandidate < limit; nextCandidate++) {
      long current = nextCandidate;
      long length = 0L;
      while (!startToLength.containsKey(current)) {
        length++;
        if ((current % 2) == 0) {
          current = current / 2;
        } else {
          current = current * 3 + 1;
        }
      }
      long totalLength = length + startToLength.get(current);
      startToLength.put(nextCandidate, totalLength);
      if (totalLength > longestLength) {
        longestStart = nextCandidate;
        longestLength = totalLength;
      }
    }
    System.out.println(longestStart);
  }
}
