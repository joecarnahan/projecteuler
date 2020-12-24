public class Painter {
  private static class Record { 
    long size = 0L;
    double time = 0.0;
    double velocity = 0.0;
    long normalizedVelocity = 0L;
  }
    
  public static void main(String[] args) {
    double baseVelocity = 300.0;
    long limit = 100000L;
    long increment = 10000L;
    long numberOfTrials = 10;
    int numberOfIncrements = (int)(limit / increment);
    Record[] records = new Record[numberOfIncrements];
    for (int index = 0; index < numberOfIncrements; index++) {
      records[index] = new Record();
      records[index].size = (index + 1) * increment;
    }
    System.out.println("Size,Time,Velocity,Normalized Velocity");
    long dummy;
    for (int trial = 1; trial <= numberOfTrials; trial++) {
      for (int index = 0; index < numberOfIncrements; index++) {
        long start = System.currentTimeMillis();

        // Do some O(N^2) work.
        for (int i = 0; i < records[index].size; i++) {
          for (int j = 0; j < records[index].size; j++) {
            dummy = i * j;
          }
        }

        // Compute the time required for this trial.
        long end = System.currentTimeMillis();
        double trialTime = ((end - start) / 1000.0);

        // Update a running average of the time and velocity.
        records[index].time += ((trialTime - records[index].time) / trial);
        records[index].velocity = records[index].size / records[index].time;
      }
    }

    for (int index = 0; index < numberOfIncrements; index++) {
      records[index].normalizedVelocity = Math.round((records[index].velocity / records[0].velocity) * baseVelocity);
      System.out.println(records[index].size + "," + 
                         records[index].time + "," + 
                         records[index].velocity + "," + 
                         records[index].normalizedVelocity);
    }
  }
}
