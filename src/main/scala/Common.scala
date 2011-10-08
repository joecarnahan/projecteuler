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

}

