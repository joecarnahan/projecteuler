import java.util.ArrayList;

public class Problem007 {
  public static void main(String[] args) {
    int primesSought = 10001;
    ArrayList<Long> knownPrimes = new ArrayList<>();
    for (long candidate = 2L; knownPrimes.size() < primesSought; candidate++) {
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
    System.out.println(knownPrimes.get(primesSought - 1));
  }
}
