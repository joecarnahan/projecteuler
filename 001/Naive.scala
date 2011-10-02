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

  def isMultipleOf(x: Int, multipleOf: Int): Boolean = ((x % multipleOf) == 0)

  def isMultipleOf(x: Int, multiplesOf: Seq[Int]): Boolean =
    multiplesOf.exists((y: Int) => ((x % y) == 0))

  def sumOfMultiplesLessThan(limit: Int, multiplesOf: Seq[Int]): Int = {

    def sumOfMultiplesLessThanRec(curr: Int, acc: Int): Int = 
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
    val defaultLimit = 1000
    val tries = 10
    if (args.length == 1) {
      val limit = Integer.valueOf(args(0))
      println("Naive algorithm took an average of " +
              Timer.timeInMilliseconds(() => 
                sumOfMultiplesLessThan(limit, List(3,5)), tries) +
              " milliseconds over " + tries + " attempts.")
    }
    else
      println(sumOfMultiplesLessThan(defaultLimit, List(3, 5)))
  }

}
