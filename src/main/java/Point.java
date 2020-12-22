import java.util.Objects;

public class Point {
  private final int x, y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean isOrigin() {
    return (this.x == 0) && (this.y == 0);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Point)) {
      return false;
    }
    Point otherPoint = (Point)o;
    return (this.x == otherPoint.x) && (this.y == otherPoint.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
