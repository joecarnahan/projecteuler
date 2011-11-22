/**
 * Sorting and sorting-related functions.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Sort {

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
  def quickSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): List[A] = 
    // TODO
    insertionSort(seq)(ordering)

  /**
   * Sorts a sequence using heapsort.
   *
   * @param toSort
   *          the sequence to sort
   * @return a sequence containing the elements of the given sequence in sorted
   *         order
   */
  def heapSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): List[A] = 
    // TODO
    insertionSort(seq)(ordering)

}

/**
 * Code for testing sorting functions.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object SortTest {

  import Sort._
  
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
    println(insertionSort(toSort))
    println(quickSort(toSort))
    println(heapSort(toSort))
  }

}
