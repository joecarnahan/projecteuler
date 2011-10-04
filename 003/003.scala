/**
 * My solution to Problem 3 of Project Euler.
 * 
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _003 {

  val defaultComposite = 600851475143L

  /**
   * Builds a stream of prime numbers.
   *
   * @return All of the prime numbers, evaluated lazily
   */
  def sieveOfErasthones(): Stream[Long] = {

    def from(value: Long): Stream[Long] =
      Stream.cons(value, from(value + 1))

    def sieve(stream: Stream[Long]): Stream[Long] =
      Stream.cons(stream.head, 
                  sieve(stream.tail.filter((x: Long) => 
                                           (x % stream.head) != 0 )))

    sieve(from(2))
  }

  def findLargestPrimeFactor(toFactor: Long, primes: Stream[Long]): Long = 
    if (primes.head >= toFactor)
      toFactor
    else if ((toFactor % primes.head) == 0)
      findLargestPrimeFactor(toFactor / primes.head, sieveOfErasthones)
    else
      findLargestPrimeFactor(toFactor, primes.tail)

  def runOn(composite: Long) = {
    println(findLargestPrimeFactor(composite, sieveOfErasthones))
    println("Naive algorithm took an average of " + 
            Timer.timeInMilliseconds(() =>
              findLargestPrimeFactor(composite, sieveOfErasthones)) +
            "ms.")
  }

  def main(args: Array[String]) =
    if (args.length == 1)
      runOn(java.lang.Long.parseLong(args(0)))
    else
      runOn(defaultComposite)

}

