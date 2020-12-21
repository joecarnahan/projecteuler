public class Solution {
  public static void main(String[] args) {
    int sum = 0;
    int previous = 0;
    int current = 1;
    while (current < 4000000) {
      int next = previous + current;
      previous = current;
      current = next; 
      if ((current % 2) == 0) {
        sum += current;
      }
    }
    System.out.println(sum);
  }
}
