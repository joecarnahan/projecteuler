import java.util.ArrayList;

public class GetPrimes {
  private static ArrayList<Long> getPrimesLessThan(Long limit) {
    ArrayList<Long> knownPrimes = new ArrayList<>();
    for (long candidate = 2L; candidate < limit; candidate++) {
      boolean isPrime = true;
      for (Long possibleDivisor : knownPrimes) {
        if ((possibleDivisor * possibleDivisor) > candidate) {
          break;
        }
        if ((candidate % possibleDivisor) == 0) {
          isPrime = false;
          break;
        }
      }
      if (isPrime) {
        knownPrimes.add(candidate);
      }
    }
    return knownPrimes;
  }

  public static void main(String[] args) {
    Long[] numbersToTest = { 4L, 6L, 9L, 16L, 17L, 30L, 35L };
    for (Long toTest : numbersToTest) {
      System.out.println("Primes less than or equal to " + toTest);
      getPrimesLessThan(toTest + 1).stream().forEach(System.out::println);
    }
  }
}
