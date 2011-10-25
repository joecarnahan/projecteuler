/**
 * My solution to problem 10 of Project Euler using a functional
 * implementation of the Sieve of Erasthones.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _010_functional {

  def getSumOfPrimesLessThan(limit: Long): Long =
    Common.sieveOfErasthones.takeWhile(_ < limit).sum

  val defaultLimit = 2000000L

  def main(args: Array[String]) =
    Runner.runLong(args, defaultLimit,
                   getSumOfPrimesLessThan(_).toString,
                   "Solution to problem 10")

}