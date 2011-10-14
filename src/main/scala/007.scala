/**
 * My solution to problem 7 of Project Euler.
 * <p>
 * The new code that I wrote for this problem is mostly in the new
 * implementation of the Sieve of Erasthones.  For small values of
 * <em>N</em>, this file worked already even with my old implementation
 * of the Sieve.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _007 {

  def getNthPrime(index: Long): Long =
    Common.sieveOfErasthones.drop(index.asInstanceOf[Int] - 1).head

  val defaultLimit = 10001L

  def main(args: Array[String]) =
    Runner.runLong(args, defaultLimit,
                   getNthPrime(_).toString,
                   "Solution to problem 7")

}
