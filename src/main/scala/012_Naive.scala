/**
 * My solution to problem 12 of Project Euler.  Not the slowest possible
 * solution, but still doesn't use many cool tricks to speed up the finding
 * of divisors.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _012_Naive {

  /**
   * Given a number, get the number of divisors that it has.
   *
   * @param toAnalyze
   *          the number to analyze
   * @return the number of natural numbers that divide evenly into 
   *         <code>toAnalyze</code>
   */
  def getNumDivisors(toAnalyze: Long): Long = {

    val sqrt = scala.math.sqrt(toAnalyze).asInstanceOf[Long]

    def search(current: Long, result: Set[Long]): Long =
      if (current < 1)
        result.size
      else if (toAnalyze % current == 0)
        search(current - 1, (result + current) + (toAnalyze / current))
      else
        search(current - 1, result)

    search(sqrt, Set())

  }

  /**
   * Walks through the list of triangle numbers and returns the first one that
   * has more divisors than the given number.
   *
   * @param numDivisors
   *          the number of divisors that we are looking for
   * @return the first triangle number that has more than 
   *         <code>numDivisors</code> divisors
   */
  def getTriangleNumberWithDivisors(numDivisors: Long): Long = {

    def search(sequence: Seq[Long]): Long =
      if (getNumDivisors(sequence.head) > numDivisors)
        sequence.head
      else
        search(sequence.tail)

    search(Common.getTriangleNumbers)

  }

  val defaultLimit = 500L

  def main(args: Array[String]) =
    Runner.run[Long](args, java.lang.Long.parseLong _, defaultLimit,
                     getTriangleNumberWithDivisors(_).toString,
                     "Naive solution to problem 12")

}
