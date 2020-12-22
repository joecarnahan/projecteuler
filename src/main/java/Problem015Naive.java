import java.util.HashMap;
import java.util.LinkedList;

public class Problem015Naive {
  private static long numberOfRoutesToOriginFrom(Point start) {
    if (start.isOrigin()) {
      return 1L;
    }
    long result = 0L;
    if (start.getX() > 0) {
      Point candidateToRight = new Point(start.getX() - 1, start.getY());
      result += numberOfRoutesToOriginFrom(candidateToRight);
    }
    if (start.getY() > 0) {
      Point candidateBelow = new Point(start.getX(), start.getY() - 1);
      result += numberOfRoutesToOriginFrom(candidateBelow);
    }
    return result;
  }

  public static void main(String[] args) {
    int limit = Integer.valueOf(args[0]);
    System.out.println(numberOfRoutesToOriginFrom(new Point(limit, limit)));
  }
}
