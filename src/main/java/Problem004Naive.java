public class Problem004Naive {
  private static int palindromes = 0;

  private static boolean isPalindrome(int number) {
    palindromes++;
    String numberString = String.valueOf(number);
    return numberString.equals((new StringBuilder(numberString)).reverse().toString());
  }

  public static void main(String[] args) {
    int firstFactor = 0;
    int secondFactor = 0;
    int largestProduct = 0;
    for (int i = 1; i < 1000; i++) {
      for (int j = 1; j < 1000; j++) {
        int product = i * j;
        if ((product > largestProduct) && isPalindrome(product)) {
          firstFactor = i;
          secondFactor = j;
          largestProduct = product;
        }
      }
    }
    System.out.println("Largest value is " + largestProduct + ", the product of " + firstFactor + " and " + secondFactor);
    System.out.println("Required computing palindromes " + palindromes + " times.");
  }
}
