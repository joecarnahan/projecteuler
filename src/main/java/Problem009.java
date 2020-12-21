public class Problem009 {
  public static void main(String[] args) {
    for (int a = 0; a < 334; a++) {
      int b = a + 1;
      int c = 1000 - a - b;
      while (b < c) {
        if ((a * a) + (b * b) == (c * c)) {
          System.out.println("  a = " + a + ",   b = " + b + ",   c = " + c);
          System.out.println("a^2 = " + (a * a) + ", b^2 = " + (b * b) + ", c^2 = " + (c * c));
          System.out.println("a * b * c = " + (a * b * c));
          System.exit(0);
        }
        b++;
        c = 1000 - a - b;
      }
    }
    throw new RuntimeException("Reached end without finding any Pythagorean triples");
  }
}
