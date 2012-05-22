/**
 * My solution to problem 14 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */

object _014 {

  /**
   * The default limit for the size of the starting number.
   */
  val defaultLimit = 1000000L

  def isEven(toTest: Long) = ((toTest % 2) == 0)

  /**
   * Interestingly, making the "solutions" map a local variable inside the
   * findLongestSequenceStartingUnder() method slows down the entire program
   * by more than a factor of two.  I'd love to know why this matters,
   * especiallly considering how much I would prefer to hide this mutable map
   * inside the smallest possible scope.
   */
  var solutions = scala.collection.mutable.HashMap[Long, Long]()

  def findLongestSequenceStartingUnder(limit: Long): Long = {
    def exploreSolution(starting: Long): Unit = {
      case class State(starting: Long, current: Long, length: Long)
      def exploreSolution(state: State): Unit =
        solutions.get(state.current) match {
          case None => 
            exploreSolution(State(state.starting,
                                  if (isEven(state.current))
                                    state.current / 2
                                  else
                                    state.current * 3 + 1, 
                                  state.length + 1))
          case Some(remainder) =>
            solutions += (state.starting -> (state.length + remainder))
        }
      exploreSolution(State(starting, starting, 0))
    }
    def findLongestSequenceStartingUnder(current: Long, best: Long): Long = 
      if (current >= limit)
        best
      else {
        exploreSolution(current)
        findLongestSequenceStartingUnder(current + 1,
                                         if (solutions(current) > solutions(best))
                                           current
                                         else
                                           best)
      }
    solutions += (1L -> 1L)
    findLongestSequenceStartingUnder(1L, 1L)
  }

  def main(args: Array[String]) =
    Runner.run[Long](args, java.lang.Long.parseLong _, defaultLimit,
                     findLongestSequenceStartingUnder(_).toString, "Problem 14")
}
