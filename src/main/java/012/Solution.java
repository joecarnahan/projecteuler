import java.util.HashSet;
import java.util.LinkedHashMap;

public class Solution {
  private static final long limit = 500L;  // Change to 500L

  private static HashSet<Long> getAllFactorsOf(Long toFactor) {
    HashSet<Long> result = new HashSet<>();
    result.add(1L);
    result.add(toFactor);
    long limit = (long)(Math.ceil(Math.sqrt(toFactor)));
    for (long candidate = 2; candidate < limit; candidate++) {
      if (toFactor % candidate == 0) {
        result.add(candidate);
        result.add(toFactor / candidate);
      }
    }
    return result;
  }

  public static void main(String[] args) {
    TriangleGenerator generator = new TriangleGenerator();
    long nextTriangleNumber;
    HashSet<Long> nextTriangleNumberFactors;
    do {
      nextTriangleNumber = generator.getNextTriangleNumber();
      nextTriangleNumberFactors = getAllFactorsOf(nextTriangleNumber);
    } while (nextTriangleNumberFactors.size() <= limit);
    System.out.println(nextTriangleNumber);
  }

  private static class TriangleGenerator {
    private long currentTriangleNumber = 0L;
    private long nextNaturalNumber = 1L;

    private long getNextTriangleNumber() {
      currentTriangleNumber += nextNaturalNumber;
      nextNaturalNumber++;
      return currentTriangleNumber;
    }
  }
}
