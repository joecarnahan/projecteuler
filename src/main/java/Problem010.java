import java.util.ArrayList;

public class Problem010 {
  private static ArrayList<Integer> getPrimesLessThan(int limit) {
    ArrayList<Integer> knownPrimes = new ArrayList<>();
    boolean[] eliminatedPrimeNumbers = new boolean[(int)limit];
    eliminatedPrimeNumbers[0] = true;
    eliminatedPrimeNumbers[1] = true;
    for (int candidate = 2; candidate < (int)(Math.ceil(Math.sqrt(limit))); candidate++) {
      if (!eliminatedPrimeNumbers[(int)candidate]) {
        knownPrimes.add(candidate);
        for (int multiple = candidate * 2; multiple < limit; multiple += candidate) {
          eliminatedPrimeNumbers[(int)multiple] = true;
        }
      }
    }
    for (int candidate = (int)(Math.ceil(Math.sqrt(limit))); candidate < limit; candidate++) {
      if (!eliminatedPrimeNumbers[(int)candidate]) {
        knownPrimes.add(candidate);
      }
    }
    return knownPrimes;
  }

  public static void main(String[] args) {
    System.out.println(getPrimesLessThan(2000000).stream().map(a -> (long)a).reduce(0L, (a, b) -> a + b));
  }
}
