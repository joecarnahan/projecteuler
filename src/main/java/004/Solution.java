public class Solution {
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
    for (int i = 999; i > 100; i--) {
      for (int j = 999; j >= i; j--) {
        int product = i * j;
        if (product <= largestProduct) {
          break;
        }
        if (isPalindrome(product)) {
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
