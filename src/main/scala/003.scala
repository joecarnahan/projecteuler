/**
 * My solution to Problem 3 of Project Euler.
 * 
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _003 {

  def findLargestPrimeFactor(toFactor: Long): Long = {

    def findLargestPrimeFactor(current: Long, primes: Stream[Long]): Long =
      if (primes.head > scala.math.sqrt(current).toLong)
        current
      else if ((current % primes.head) == 0)
        findLargestPrimeFactor(current / primes.head, Common.sieveOfErasthones)
      else
        findLargestPrimeFactor(current, primes.tail)

    findLargestPrimeFactor(toFactor, Common.sieveOfErasthones)
  }

  val defaultComposite = 600851475143L

  def main(args: Array[String]) =
    Runner.runLong(args, defaultComposite,
                   findLargestPrimeFactor(_).toString,
                   "Solution to problem 3")

}

