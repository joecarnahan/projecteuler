/**
 * My solution to problem 14 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */

object _014_Naive {

  /**
   * The default limit for the size of the starting number.
   */
  val defaultLimit = 1000000L

  def isEven(toTest: Long) = ((toTest % 2) == 0)

  def findLongestSequenceStartingUnder(limit: Long): Long = {
    case class Solution(startingValue: Long, currentValue: Long, length: Long)
    def exploreSolution(startingValue: Long): Solution = {
      def exploreSolution(current: Solution): Solution = {
        if (current.currentValue == 1)
          current
        else
          exploreSolution(Solution(current.startingValue,
                                   if (isEven(current.currentValue))
                                     current.currentValue / 2
                                   else
                                     current.currentValue * 3 + 1, 
                                   current.length + 1))
      }
      exploreSolution(Solution(startingValue, startingValue, 1))
    }
    def findLongestSequenceStartingUnder(currentValue: Long, best: Solution):
        Solution = {
      if (currentValue >= limit)
        best
      else {
        val current = exploreSolution(currentValue)
        if (current.length > best.length)
          findLongestSequenceStartingUnder(currentValue + 1, current)
        else
          findLongestSequenceStartingUnder(currentValue + 1, best)
      }
    }
    findLongestSequenceStartingUnder(1L, Solution(1L, 1L, 1L)).startingValue
  }

  def main(args: Array[String]) =
    Runner.run[Long](args, java.lang.Long.parseLong _, defaultLimit,
                     findLongestSequenceStartingUnder(_).toString, "Problem 14")
}
