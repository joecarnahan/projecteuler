/**
 * Functions relating to prime numbers and factors.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Primes {

  /**
   * Gets all of the natural numbers starting with the given number.
   *
   * @param value
   *          the number to start from
   * @return a stream of numbers starting from the given one
   */
  def from(value: Long): Stream[Long] =
    Stream.cons(value, from(value + 1))

  /**
   * Simple implementation of the Sieve of Erasthones, as demonstrated in
   * the documentation for the Scala Stream class.
   *
   * @return All of the prime numbers, evaluated lazily
   */
  def simpleSieveOfErasthones(): Stream[Long] = {

    def sieve(stream: Stream[Long]): Stream[Long] =
      Stream.cons(stream.head, 
                  sieve(stream.tail.filter((x: Long) => 
                                           (x % stream.head) != 0 )))

    sieve(from(2))

  }

  /**
   * Builds a stream of prime numbers.
   *
   * @return All of the prime numbers, evaluated lazily
   */
  def sieveOfErasthones(): Stream[Long] = {

    /**
     * Yields a function to check if a given number is divisible by
     * any of the given prime numbers.  We assume that the given
     * numbers are sorted in ascending order, so that we can cut off
     * the search if we get to a prime number that is larger than
     * the square root of the given number.
     *
     * @param primes
     *          the list of prime numbers to check
     * @return a function that checks if a number is prime relative
     *         to the given list of prime numbers
     */
    def divisibleByAny(primes: List[Long]): Long => Boolean =

      (candidate: Long) => {

        val squareRoot = scala.math.sqrt(candidate).asInstanceOf[Long]

        def search(list: List[Long]): Boolean =
          list match {
            case Nil => false
            case head :: tail => 
              if (head > squareRoot)
                false
              else if ((candidate % head) == 0)
                true 
              else 
                search(tail)
          }

        search(primes)

      }

    def sieve(stream: Stream[Long], primes: List[Long]): Stream[Long] =
      Stream.cons(stream.head, 
                  sieve(stream.tail.dropWhile(divisibleByAny(primes.reverse)),
                        stream.head :: primes))

    sieve(from(2), List[Long]())

  }

  /**
   * Gets all of the prime factors of a given number, including duplicates.
   * So, for example, when given 25, this function returns List(5, 5) as its
   * result.
   *
   * @param toFactor
   *          the number to factor
   */
  def getFactors(toFactor: Long): List[Long] = {

    def getFactorsRec(remainingToFactor: Long, 
                      primes: Stream[Long], 
                      factors: List[Long]): List[Long] =
      if (remainingToFactor == primes.head)
        primes.head :: factors
      else if ((remainingToFactor % primes.head) == 0L)
        getFactorsRec(remainingToFactor / primes.head,
                      sieveOfErasthones,
                      primes.head :: factors)
      else
        getFactorsRec(remainingToFactor, primes.tail, factors)

    getFactorsRec(toFactor, sieveOfErasthones, List[Long]())
    
  }

}
