import java.util.HashSet;

public class TriangleGenerator {
  private long currentTriangleNumber = 0L;
  private long nextNaturalNumber = 1L;

  public long getNextTriangleNumber() {
    currentTriangleNumber += nextNaturalNumber;
    nextNaturalNumber++;
    return currentTriangleNumber;
  }
}
