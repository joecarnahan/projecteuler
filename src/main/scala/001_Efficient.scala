/**
 * This is the solution that is provided by Project Euler after you present
 * your own correct answer to question #1.
 *
 * The key is that you have to remember that there is a closed form expression
 * for the sum of all integers from 1 to N, namely (n * (n + 1) / 2).  When
 * counting to 1000, this closed form isn't significantly faster than just
 * iterating over the integers from 1 to 1000, but it does show a performance
 * improvement when you run it from 1 to 1000000.
 *
 * Calling this with no command-line arguments causes it to run once and
 * and show the sum of all multiples of 3 or 5 that are less than 1000.
 * Calling this with a single number as the command-line argument causes it
 * to run repeatedly finding the sum of all multiples of 3 or 5 that are less
 * than that number and to print out the time required to compute that result.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com).
 */
object Efficient {

  def sumOfMultiplesLessThan(limit: Long, multipleOf: Long): Long = {
    val p = (limit - 1) / multipleOf
    multipleOf * p * (p + 1) / 2
  }

  def sumOfMultiplesOf3And5LessThan(limit: Long): Long =
    sumOfMultiplesLessThan(limit, 3) + 
    sumOfMultiplesLessThan(limit, 5) -
    sumOfMultiplesLessThan(limit, 15)

  def main(args: Array[String]) = {
    if (args.length == 1) {
      val limit = java.lang.Long.parseLong(args(0))
      println("Efficient algorithm took an average of " +
              Timer.timeInMilliseconds(() => 
                sumOfMultiplesOf3And5LessThan(limit)) +
              " milliseconds.")
    }
    else
      println(sumOfMultiplesOf3And5LessThan(1000L))
  }

}
