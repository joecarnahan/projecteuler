/**
 * My solution to problem 6 of Project Euler.
 * <p>
 * Before I could figure out the closed-form solution for the 
 * sum-of-squares function <em>f()</em>, I needed a hint that I
 * should start by looking at <em>f(0)</em>, <em>f(1)</em>,
 * <em>f(2)</em>, and <em>f(3)</em>, and solve for the constants
 * in a cubic equation that would produce those values.  
 * <p>
 * Even then, it took me a few tries to get my algebra right -
 * It's been a long time since I had to solve multiple equations
 * for multiple unknowns. ;-)
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
   * Computes the sum of all natural numbers up to and including the given
   * number.
   *
   * @param limit
   *          the limit of the arithmetic progression
   * @return the sum of the integers from 1 through <code>limit</code>
   */
  def arithmeticProgression(limit: Long): Long =
    limit * (limit + 1) / 2

  /**
   * Computes the sum of all squares of all natural numbers up to and
   * including the given number.
   *
   * @param limit
   *          the largest number to square and add to this sum
   * @return the sum of the squares of the integers from 1 through
   *         <code>limit</code>
   */
  def sumOfSquares(limit: Long): Long =
    limit * ((2 * limit) + 1) * (limit + 1) / 6

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
  def differenceBetweenSumOfSquaresAndSquareOfSums(limit: Long): Long = {
    val sum = arithmeticProgression(limit)
    (sum * sum) - sumOfSquares(limit)
  }

  val defaultLimit = 100L

  def main(args: Array[String]) =
    Runner.run(args, defaultLimit,
               differenceBetweenSumOfSquaresAndSquareOfSums(_).toString,
               "Solution to problem 6")

}
