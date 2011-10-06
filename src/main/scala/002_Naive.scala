/**
 * My solution to problem 2 of Project Euler.
 *
 * Calling this with no command-line arguments causes it to run once and
 * and show the sum of all even Fibonacci numbers less than 4000000.
 * Calling this with a single number as the command-line argument causes it
 * to run repeatedly finding the sum of all even Fibonacci numbers less than
 * than that number and to print out the time required to compute that result.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _002_Naive {

  def isEven(toTest: Long) = ((toTest % 2) == 0)

  def computeValuesUpTo(limit: Long): Long = {

    def computeValues(curr: Long, prev: Long, sum: Long): Long =
      if (curr >= limit)
        sum
      else
        computeValues(curr + prev, curr,
                      if (isEven(curr)) sum + curr else sum)

    computeValues(1, 0, 0)

  }

  val defaultLimit = 4000000L

  def main(args: Array[String]) = 
    Runner.run(args, defaultLimit,
               computeValuesUpTo(_).toString,
               "Naive solution to problem 2")

}
