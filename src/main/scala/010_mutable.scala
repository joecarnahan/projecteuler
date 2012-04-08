/**
 * My solution to problem 10 of Project Euler using a mutable bit set
 * to implement the Sieve of Erasthones.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _010_mutable {

  /**
   * Gets all of the prime numbers that are less than the given value.
   */
  def sieveOfErasthones(limit: Int): Seq[Int] = {
    // Construct the list of numbers to only include odd values
    val numbers = scala.collection.mutable.BitSet((3 to limit by 2): _*)
    // Then, put 2 back into the list
    numbers += 2
    val squareRoot = scala.math.sqrt(limit)
    var prime = 3
    while (prime <= squareRoot) {
      var j = prime * prime
      while (j <= limit) {
        numbers -= j
        j += prime
      }
      prime += 2
      while (!numbers(prime)) prime += 1;
    }
    numbers.toStream
  }

  def getSumOfPrimesLessThan(limit: Long): Long =
    sieveOfErasthones(limit.asInstanceOf[Int]).map(_.asInstanceOf[Long]).sum

  val defaultLimit = 2000000L

  def main(args: Array[String]) =
    Runner.run[Long](args, java.lang.Long.parseLong _, defaultLimit,
                     getSumOfPrimesLessThan(_).toString,
                     "Mutable-state solution to problem 10")

}
