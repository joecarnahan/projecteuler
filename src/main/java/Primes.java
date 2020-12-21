import java.util.ArrayList;
import java.util.Collections;

public class Primes {
  public static ArrayList<Integer> getPrimesLessThan(Integer limit) {
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
}
