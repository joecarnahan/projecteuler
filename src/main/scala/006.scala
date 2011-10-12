/**
 * My solution to problem 6 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _006 {

  /**
   * Naive solution, which just sums the values as well as the squares of the
   * values by iterating over them.  Useful for checking my work.
   *
   * @param limit
   *          the number up to which (inclusive) we want to compute the sum
   *          of squares and the square of the sum
   * @return the difference between the square of the sum and the sum of the
   *         squares
   */
  def naiveSolution(limit: Long): Long = {
    
    def iterate(current: Long, sum: Long, sumOfSquares: Long): Long =
      if (current > limit)
        (sum * sum) - sumOfSquares
      else
        iterate(current + 1, sum + current, sumOfSquares + (current * current))

    iterate(1, 0, 0)

  }

  /**
   * Computes the difference between the square of the sum of numbers up to
   * given value and the sum of the squares of the numbers up to the given
   * value.
   *
   * @param limit
   *          the number up to which (inclusive) we want to compute the sum
   *          of squares and the square of the sum
   * @return the difference between the square of the sum and the sum of the
   *         squares
   */
  def differenceBetweenSumOfSquaresAndSquareOfSums(limit: Long): Long = 
    naiveSolution(limit)

  val defaultLimit = 100L

  def main(args: Array[String]) =
    Runner.run(args, defaultLimit,
               differenceBetweenSumOfSquaresAndSquareOfSums(_).toString,
               "Solution to problem 6")

}
