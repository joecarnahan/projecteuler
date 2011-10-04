/**
 * My solution to Problem 3 of Project Euler.
 * 
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _003 {

  val defaultComposite = 600851475143L

  /**
   * Finds all prime numbers lower than or equal to the given limit, sorted
   * from smallest to largest.
   *
   * @param limit
   *          the largest number that might be returned by this method.
   * @return All of the prime numbers lower than or equal to the given limit
   */
  def sieveOfErasthones(limit: Long): List[Long] = {

    def from(value: Long): Stream[Long] =
      Stream.cons(value, from(value + 1))

    def sieve(stream: Stream[Long]): Stream[Long] =
      Stream.cons(stream.head, 
                  sieve(stream.tail.filter((x: Long) => 
                                           (x % stream.head) != 0 )))

    sieve(from(2)).takeWhile(_ <= limit).toList
  }

  def findLargestPrimeFactor(composite: Long): Long = {
    val primes = sieveOfErasthones(composite / 2).reverse
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

