/**
 * Commmon functions that are shared by other problems' solutions.
 *
 * If this grows to more than three or four functions, I should break it
 * up into separate classes. 
 * 
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Common {

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

