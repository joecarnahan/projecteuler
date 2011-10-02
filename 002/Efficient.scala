/**
 * An improved solution to Problem 2 of Project Euler, where the
 * isEven() check is skipped in favor of just summing every third
 * Fibonacci number.
 *
 * Note that the "improved" version does not seem to take any less time
 * than the naive version, as both complete almost instantly.
 *
 * Calling this with no command-line arguments causes it to run once and
 * and show the sum of all even Fibonacci numbers less than 4000000.
 * Calling this with a single number as the command-line argument causes it
 * to run repeatedly finding the sum of all even Fibonacci numbers less than
 * than that number and to print out the time required to compute that result.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Efficient {

  def computeValuesUpTo(limit: Long): Long = {

    def computeValues(sum: Long, a: Long, b: Long, c: Long): Long =
      if (c >= limit)
        sum
      else {
        val newA = b + c
        val newB = c + newA
        val newC = newA + newB
        computeValues(sum + c, newA, newB, newC)
      }

    computeValues(0, 1, 1, 2)

  }

  def main(args: Array[String]) = 
    if (args.length == 1)
      println("Efficient algorithm required an average of " + 
              Timer.timeInMilliseconds(() =>
                computeValuesUpTo(java.lang.Long.parseLong(args(0)))) +
              " milliseconds.")
    else
      println(computeValuesUpTo(4000000L))

}
