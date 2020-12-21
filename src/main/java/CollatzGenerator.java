import java.util.Iterator;

public class CollatzGenerator implements Iterable<Long> {
  private long currentNumber;
  private boolean done;

  public Iterator<Long> iterator() {
    return new Iterator<Long>() {
      public boolean hasNext() {
        return !done;
      }

      public Long next() {
        if (done) {
          throw new IllegalStateException("Called next() after last value was already returned");
        }
        long result = currentNumber;
        if (result == 1) {
          done = true;
        } else {
          if ((currentNumber % 2) == 0) {
            currentNumber = currentNumber / 2;
          } else {
            currentNumber = currentNumber * 3 + 1;
          }
        }
        return result;
      }
    };
  }

  public CollatzGenerator(long start) {
    if (start < 1) {
      throw new IllegalArgumentException("Initial value must be a positive number, but " + start + " was given");
    }
    currentNumber = start;
    done = false;
  }
  
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("Expected 1 argument, got " + args.length);
    }
    long start = Long.valueOf(args[0]);
    CollatzGenerator generator = new CollatzGenerator(start);
    for (Long nextNumber : generator) {
      System.out.println(nextNumber);
    }
  }
}
