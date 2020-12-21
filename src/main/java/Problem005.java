import java.util.ArrayList;
import java.util.Collections;

public class Problem005 {

  private static final int limit = 20;
  private static ArrayList<Integer> primesLessThanLimit;
  private static int numberOfPrimes;

  private static ArrayList<Integer> getPrimeFactorListOf(int toFactor) {
    // System.out.print("Factored " + toFactor + " into this list: ");
    ArrayList<Integer> primeFactorList = new ArrayList<>();
    int nextCandidate = 2;
    do {
      if ((toFactor % nextCandidate) == 0) {
        primeFactorList.add(nextCandidate);
        toFactor = toFactor / nextCandidate;
      } else {
        nextCandidate++;
      }
    } while ((nextCandidate * nextCandidate) <= toFactor);
    if (toFactor > 1) {
      primeFactorList.add(toFactor);
    }
    // System.out.println(primeFactorList); 
    return primeFactorList;
  }

  private static ArrayList<Integer> getPrimeFactorCountsOf(int toFactor) {
    ArrayList<Integer> primeFactorList = getPrimeFactorListOf(toFactor);
    ArrayList<Integer> primeFactorCounts = new ArrayList<>(numberOfPrimes);
    for (int i = 0; i < numberOfPrimes; i++) {
      primeFactorCounts.add(Integer.valueOf(0));
    }
    for (int i = 0; i < numberOfPrimes; i++) {
      primeFactorCounts.set(i, Collections.frequency(primeFactorList, primesLessThanLimit.get(i)));
    }
    // System.out.println("Counted this frequency of prime factors: " + primeFactorCounts);
    return primeFactorCounts;
  }

  public static void main(String[] args) {
    primesLessThanLimit = Primes.getPrimesLessThan(limit);
    numberOfPrimes = primesLessThanLimit.size();
    // System.out.println("Got these " + numberOfPrimes + " primes: " + primesLessThanLimit);
    ArrayList<Integer> peakPrimeFactorCounts = new ArrayList<>(numberOfPrimes);
    for (int primeIndex = 0; primeIndex < numberOfPrimes; primeIndex++) {
      peakPrimeFactorCounts.add(Integer.valueOf(0));
    }
    for (int numberIndex = 2; numberIndex <= limit; numberIndex++) {
      ArrayList<Integer> primeFactorCounts = getPrimeFactorCountsOf(numberIndex);
      for (int primeIndex = 0; primeIndex < numberOfPrimes; primeIndex++) {
        if (peakPrimeFactorCounts.get(primeIndex) < primeFactorCounts.get(primeIndex)) {
          peakPrimeFactorCounts.set(primeIndex, primeFactorCounts.get(primeIndex));
        }
      }
    }
    // System.out.println("Peak frequencies of prime factors: " + peakPrimeFactorCounts);
    int result = 1;
    for (int i = 0; i < numberOfPrimes; i++) {
      result = result * (int)(Math.pow(primesLessThanLimit.get(i), peakPrimeFactorCounts.get(i)));
    }
    System.out.println(result);
  }
}
