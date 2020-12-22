import java.util.HashMap;

public class Problem015 {
  private static final HashMap<Point, Long> knownSolutions = new HashMap<>();

  private static long numberOfRoutesToOriginFrom(Point start) {
    if (start.isOrigin()) {
      return 1L;
    }
    long result = 0L;
    if (start.getX() > 0) {
      Point candidateToRight = new Point(start.getX() - 1, start.getY());
      if (knownSolutions.containsKey(candidateToRight)) {
        result += knownSolutions.get(candidateToRight);
      } else {
        result += numberOfRoutesToOriginFrom(candidateToRight);
      }
    }
    if (start.getY() > 0) {
      Point candidateBelow = new Point(start.getX(), start.getY() - 1);
      if (knownSolutions.containsKey(candidateBelow)) {
        result += knownSolutions.get(candidateBelow);
      } else {
        result += numberOfRoutesToOriginFrom(candidateBelow);
      }
    }
    knownSolutions.put(start, result);
    return result;
  }


  public static void main(String[] args) {
    int limit = Integer.valueOf(args[0]);
    System.out.println(numberOfRoutesToOriginFrom(new Point(limit, limit)));
  }
}
