import java.math.BigInteger;

public class Problem016 {
  public static void main(String args[]) {
    int limit = 1000;
    BigInteger product = BigInteger.ONE;
    BigInteger two = new BigInteger("2");
    for (int i = 0; i < limit; i++) {
      product = product.multiply(two) ;
    }
    System.out.println(product.toString().chars().map(i -> Character.getNumericValue(i)).sum());
  }
}
