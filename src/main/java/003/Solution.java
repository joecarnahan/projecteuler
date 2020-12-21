import java.util.LinkedHashSet;
import java.util.ArrayList;

public class Solution {
  public static void main(String[] args) {
    long toFactor = 600851475143L; // Values to try: 4 6 9 16 30 13195 600851475143 
    LinkedHashSet<Long> primeFactorSet = new LinkedHashSet<>();
    long nextCandidate = 2;
    do {
      if ((toFactor % nextCandidate) == 0) {
        primeFactorSet.add(nextCandidate);
        toFactor = toFactor / nextCandidate;
      } else {
        nextCandidate++;
      }
    } while ((nextCandidate * nextCandidate) <= toFactor);
    primeFactorSet.add(toFactor);
    ArrayList<Long> primeFactorList = new ArrayList<>(primeFactorSet);
    System.out.println(primeFactorList.get(primeFactorList.size() - 1));
  }
}
