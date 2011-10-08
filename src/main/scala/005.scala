/**
 * My solution to problem 5 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _005 {

  def findSmallestProductOfNumbersUpTo(limit: Long): Long =
    Common.sieveOfErasthones.takeWhile(_ <= limit).foldLeft(1L)(_ * _)

  val defaultLimit = 20L

  def main(args: Array[String]) =
    Runner.run(args, defaultLimit,
               findSmallestProductOfNumbersUpTo(_).toString,
               "Solution to problem 5")

}
