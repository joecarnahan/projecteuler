/**
 * Naive solution (and thus the one I came up with) for Project Euler 
 * problem #1.
 *
 * Calling this with no command-line arguments causes it to run once and
 * and show the sum of all multiples of 3 or 5 that are less than 1000.
 * Calling this with a single number as the command-line argument causes it
 * to run repeatedly finding the sum of all multiples of 3 or 5 that are less
 * than that number and to print out the time required to compute that result.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Naive {

  def isMultipleOf(x: Long, multipleOf: Long): Boolean = ((x % multipleOf) == 0)

  def isMultipleOf(x: Long, multiplesOf: Seq[Long]): Boolean =
    multiplesOf.exists((y: Long) => ((x % y) == 0))

  def sumOfMultiplesLessThan(limit: Long, multiplesOf: Seq[Long]): Long = {

    def sumOfMultiplesLessThanRec(curr: Long, acc: Long): Long = 
      if (curr < limit)
          sumOfMultiplesLessThanRec(curr + 1, 
                                    if (isMultipleOf(curr, multiplesOf))
                                      acc + curr
                                    else
                                      acc)
      else
        acc

    sumOfMultiplesLessThanRec(0, 0)
  }

  def main(args: Array[String]) = {
    val multiplesOf = List(3L, 5L)
    if (args.length == 1) {
      val limit = java.lang.Long.parseLong(args(0))
      println("Naive algorithm took an average of " +
              Timer.timeInMilliseconds(() => 
                sumOfMultiplesLessThan(limit, multiplesOf)) +
              " milliseconds.")
    }
    else
      println(sumOfMultiplesLessThan(1000L, multiplesOf))
  }

}