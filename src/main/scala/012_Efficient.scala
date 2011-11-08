/**
 * The given solution to Problem 12, which finds the number of divisors much
 * more quickly by examining each number as a product of powers of prime 
 * numbers.  I rewrote the logic to work without any mutable variables, but 
 * the algorithm itself (and the cryptic variable naming scheme) comes from 
 * rayfil's given solution to Problem 12.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _012_Efficient {

  /**
   * Computes the triangle number with black magic.  (More or less - Still
   * trying to understand the math myself.)
   *
   * @param numDivisors
   *          the number of divisors that we are looking for
   * @return the first triangle number that has more than 
   *         <code>numDivisors</code> divisors
   */
  def getTriangleNumberWithDivisors(numDivisors: Long): Long = {

    def findExponentAndN1(exponent: Long, n1: Long, prime: Long): (Long, Long) =
      if ((n1 % prime) == 0)
        findExponentAndN1(exponent + 1, n1 / prime, prime)
      else
        (exponent, n1)

    def findDn1(primes: Seq[Long], n1: Long, dn1: Long): Long = {
      val prime = primes.head
      if ((prime * prime) > n1)
        dn1 * 2
      else { 
        val (exponent, newN1) = findExponentAndN1(1, n1, prime)
        if (newN1 == 1)
          dn1 * exponent
        else
          findDn1(primes.tail, newN1, dn1 * exponent)
      }
    }

    def findTriangleNumberWithDivisors(n: Long, dn: Long): Long = {
      val newN = n + 1
      val n1 = if ((newN % 2) == 0) newN / 2 else newN
      val dn1 = findDn1(Common.sieveOfErasthones, n1, 1)
      if ((dn * dn1) > numDivisors)
        newN * n / 2
      else
        findTriangleNumberWithDivisors(newN, dn1)
    }

    findTriangleNumberWithDivisors(3, 2)
 
  }

  val defaultLimit = 500L

  def main(args: Array[String]) =
    Runner.runLong(args, defaultLimit,
                   getTriangleNumberWithDivisors(_).toString,
                   "Efficient solution to problem 12")

}
