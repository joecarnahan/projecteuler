import java.util.ArrayList;

public class Problem010Naive {
  private static ArrayList<Integer> getPrimesLessThan(int limit) {
    ArrayList<Integer> knownPrimes = new ArrayList<>();
    for (int candidate = 2; candidate < limit; candidate++) {
      boolean isPrime = true;
      for (Integer possibleDivisor : knownPrimes) {
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
    System.out.println(getPrimesLessThan(2000000).stream().map(a -> (long)a).reduce(0L, (a, b) -> a + b));
  }
}
