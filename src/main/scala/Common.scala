/**
 * Common functions that are shared by other problems' solutions.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Common {

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

}
