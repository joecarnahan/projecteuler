public class Problem006 {
  public static void main(String[] args) {
    long limit = 100L;
    long sum = 0L;
    long sumOfSquares = 0L;
    for (long i = 1L; i <= limit; i++) {
      sum += i;
      sumOfSquares += (i * i);
    }
    long squareOfSum = sum * sum;
    System.out.println("Sum of squares is " + sumOfSquares + ", square of sum is " + squareOfSum + ", difference is " + (squareOfSum - sumOfSquares));
  }
}
