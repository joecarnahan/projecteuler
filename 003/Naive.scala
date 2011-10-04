/**
 * My solution to Problem 3 of Project Euler.
 * 
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Naive {

  val defaultComposite = 600851475143L

  /**
   * Finds all prime numbers lower than or equal to the given limit.
   *
   * @param limit
   *          the largest number that might be returned by this method.
   * @return All of the prime numbers lower than or equal to the given limit
   */
  def sieveOfErasthones(limit: Long) = {

    val squareRoot = scala.math.sqrt(limit).toLong

    def runSieve(primes: List[Long], remaining: Seq[Long]): List[Long] =
      if (remaining.isEmpty)
        primes
      else if (remaining.head >= squareRoot)
        primes ++ remaining
      else
        runSieve(remaining.head :: primes, 
                 remaining.tail.filterNot((x: Long) => 
                                          ((x % remaining.head) == 0)))

    runSieve(List[Long](), 2L to limit)
  }

  def findLargestPrimeFactor(composite: Long): Long = {
    val primes = 
      sieveOfErasthones(composite / 2).sorted(scala.math.Ordering.Long.reverse)
    primes.find((x: Long) => 
      ((composite % x) == 0)).getOrElse(sys.error("Given value " + composite + 
                                                  " is not a composite number"))
  }

  def runOn(composite: Long) = {
    println(findLargestPrimeFactor(composite))
    println("Naive algorithm took an average of " + 
            Timer.timeInMilliseconds(() =>
              findLargestPrimeFactor(composite)) +
            "ms.")
  }

  def main(args: Array[String]) =
    if (args.length == 1)
      runOn(java.lang.Long.parseLong(args(0)))
    else
      runOn(defaultComposite)

}
