public class Problem005Naive {
  public static void main(String[] args) {
    int result = 0;
    for (int candidate = 20; result == 0; candidate++) {
      result = candidate;
      for (int factor = 20; (factor > 6) && (result != 0); factor--) {
        if ((candidate % factor) != 0) {
          result = 0;
        }
      }
    }
    System.out.println(result);
  }
}
