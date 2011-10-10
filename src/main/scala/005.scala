/**
 * My solution to problem 5 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _005 {

  def findSmallestProductOfNumbersUpTo(limit: Long): Long = {

    /**
     * Merges the two lists, returning a list that contains every element
     * from both lists.  Specifically, if an element appears <em>x</em> times
     * in one list and <em>y</em> times in the other list, then it appears
     * <em>max(x, y)</em> times in the result of this function.
     *
     * @param first
     *          one list to merge
     * @param second
     *          another list to merge
     */
    def mergeLists[T](first: List[T], second: List[T]): List[T] = {

      def mergeLists(first: List[T], second: List[T], acc: List[T]): List[T] =
        first match {
          case Nil => second ++ acc
          case next :: rest => 
            mergeLists(rest, Common.removeFirst(second, next), next :: acc)
        }

      mergeLists(first, second, Nil)

    }

    /**
     * Adds all of the factors of the given number into the given list of
     * numbers.  See the <code>mergeLists()</code> method for more information
     * on how lists of factors are merged.
     *
     * @param toFactor
     *          the number to factor
     * @param factors
     *          the list of factors into which the given number's factors
     *          should be merged
     */
    def addFactorsFrom(toFactor: Long, factors: List[Long]): List[Long] =
      mergeLists(Common.getFactors(toFactor), factors)

    def collectFactors(next: Long, factors: List[Long]): List[Long] =
      if (next > limit)
        factors
      else
        collectFactors(next + 1, addFactorsFrom(next, factors))

    collectFactors(2L, List[Long]()).foldLeft(1L)(_ * _)

  }

  val defaultLimit = 20L

  def main(args: Array[String]) =
    Runner.run(args, defaultLimit,
               findSmallestProductOfNumbersUpTo(_).toString,
               "Solution to problem 5")

}
