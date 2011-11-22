/**
 * Commmon functions that are shared by other problems' solutions.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Common {

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

  /**
   * Removes the first element of the given list that matches the given
   * predicate, if any.
   *
   * @param list
   *          the list from which to remove the element
   * @param toRemove
   *          a predicate indicating which element to remove
   * @return a new list with the selected object removed, or the same
   *         list if no objects satisfy the given predicate
   */
  def removeFirst[T](list: List[T], toRemove: (T) => Boolean): List[T] = {
    def search(toProcess: List[T], processed: List[T]): List[T] =
      toProcess match {
        case Nil => list
        case head :: tail => 
          if (toRemove(head))
            processed.reverse ++ tail
          else
            search(tail, head :: processed)
      }
    search(list, Nil)
  }

  /**
   * Removes the first occurrence of the given object from the given list.
   *
   * @param list
   *          the list from which to remove the element
   * @param toRemove
   *          the element to remove
   * @return a new list with the selected object removed, or the same
   *         list if the given object is not in the list
   */
  def removeFirst[T](list: List[T], toRemove: T): List[T] = 
    removeFirst(list, (t: T) => t == toRemove)

  /**
   * Finds the sublist of the given list with the given length that has the
   * largest product.
   * 
   * @param toSearch
   *          the list to search
   * @param sequenceSize
   *          the size of sublists of the given list to search
   * @return the sublist of the given list with the given length that has the
   *         largest product
   */
  def findLargestProduct[T](toSearch: List[T], sequenceSize: Int)
                           (implicit num: Numeric[T]): List[T] = {

    /**
     * Searches the remaining digits in the list for any sequence that is the
     * same length as the given sequence and that has a larger product.
     *
     * @param previous
     *          the last sequence of digits that we considered
     * @param best
     *          the largest product found so far
     * @param remaining
     *          the remaining digits to consider
     * @return the largest product of a sequence in this string
     */
    def search(current: List[T], bestList: List[T], best: T, remaining: List[T]): List[T] =
      remaining match {
        case Nil => bestList
        case head :: tail => {
          val newList = current.tail ++ List(head)
          if (num.gt(head, current.head)) {
            val newProduct = newList.product(num)
            if (num.gt(newProduct, best))
              search(newList, newList, newProduct, tail)
            else
              search(newList, bestList, best, tail)
          }
          else
            search(newList, bestList, best, tail)
        }
      }

    if (toSearch.size < sequenceSize)
      toSearch
    val (initialList, remainder) = toSearch.splitAt(sequenceSize)
    search(initialList, initialList, initialList.product(num), remainder)

  }

  /**
   * Builds a stream of triangle numbers.
   *
   * @return a lazily-constructed sequence of triangle numbers
   */
  def getTriangleNumbers: Seq[Long] = {

    def next(first: Long, nextNatural: Long): Stream[Long] =
      Stream.cons(first, next(first + nextNatural, nextNatural + 1))

    next(1, 2)

  }

  /**
   * Inserts the given element into the given list in sorted order.
   *
   * @param element
   *          the element to insert
   * @param list
   *          the sorted list into which the element should be inserted
   * @return the result of inserting the given element into the given list
   */
  def insert[A](element: A, list: List[A])(implicit ordering: Ordering[A]): List[A] =
    list.foldLeft((false, List[A]()))((state: (Boolean, List[A]), next: A) => {
      val (inserted, processed) = state
      if (inserted)
        (true, next :: processed)
      else if (ordering.gt(element, next))
        (false, next :: processed)
      else
        (true, next :: (element :: processed))
    }) match {
      case (true, result) => result.reverse
      case (false, result) => (element :: result).reverse
    }

  /**
   * Sorts a sequence using insertion sort.
   * 
   * @param toSort
   *          the sequence to sort
   * @return a sequence containing the elements of the given sequence in sorted
   *         order
   */
  def insertionSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): List[A] = {

    def rec(processed: List[A], remaining: Seq[A]): List[A] = 
      if (remaining.isEmpty)
        processed
      else
        rec(insert(remaining.head, processed), remaining.tail)

    if (seq.isEmpty)
      List[A]()
    else
     rec(List(seq.head), seq.tail)

  }

  /**
   * Sorts a sequence using quicksort.
   *
   * @param toSort
   *          the sequence to sort
   * @return a sequence containing the elements of the given sequence in sorted
   *         order
   */
  def quickSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): List[A] = insertionSort(seq)(ordering)

}

/**
 * Code for testing sorting functions.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object SortTest {
  
  /**
   * Generates a random list of positive and negative integers of similar
   * magnitude to the given number with the given number of values.
   *
   * @param size
   *          both a limit for how large the random numbers should be and the
   *          size of the randomly-generated list
   * @return a list of randomly-generated integers of the given size with
   *         values of the given magnitude
   */
  def randomList(size: Int): List[Int] = {

    def rng = new scala.util.Random()

    def rec(acc: List[Int], sizeLeft: Int): List[Int] =
      if (sizeLeft <= 0)
        acc
      else
        rec((rng.nextInt(size * 2) - size) :: acc, sizeLeft - 1)

    rec(List[Int](), size)

  }

  /**
   * Parses the given sequence of strings and turns it into a list of integers.
   *
   * @param toParse
   *          the strings to parse, which must all be string representations of
   *          integers
   * @return the given strings interpreted as integers
   */
  def parseInts(toParse: Seq[String]): List[Int] = {

    def rec(acc: List[Int], remaining: Seq[String]): List[Int] =
      if (remaining.isEmpty)
        acc.reverse
      else
        rec(java.lang.Integer.parseInt(remaining.head) :: acc, remaining.tail)

    rec(List[Int](), toParse)

  }

  /**
   * Generates a random list of integers or takes a list of integers as 
   * arguments and sorts them using both quicksort and insertion sort.
   *
   * @param args
   *          either an empty array, indicating that a list of 10 random
   *          integers should be sorted, or a single integer, indicating
   *          that a random list of the given size should be sorted, or a
   *          list of two or more integers that should be sorted
   */
  def main(args: Array[String]) = {
    val toSort =
      if (args.length == 0)
        randomList(10)
      else if (args.length == 1)
        randomList(java.lang.Integer.parseInt(args(0)))
      else
        parseInts(args)
    println(Common.insertionSort(toSort))
    println(Common.quickSort(toSort))
  }

}
