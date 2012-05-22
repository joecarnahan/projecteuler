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

  def buildPowerMap: Map[Long, Long] = {
    def buildPowerMap(exponent: Long, map: Map[Long,Long]): Map[Long, Long] =
      if ((1L << exponent) > (Long.MaxValue / 2))
        map + ((1L << exponent) -> exponent)
      else
        buildPowerMap(exponent + 1, map + ((1L << exponent) -> exponent))
    buildPowerMap(0, Map())
  }

  val powerMap: Map[Long, Long] = buildPowerMap

  def powerOfTwo(toTest: Long): Option[Long] = powerMap.get(toTest)

  def findLongestSequenceStartingUnder(limit: Long): Long = {
    case class Solution(startingValue: Long, currentValue: Long, length: Long)
    def exploreSolution(startingValue: Long): Solution = {
      def exploreSolution(current: Solution): Solution = {
        powerOfTwo(current.currentValue) match {
          case None => 
            exploreSolution(Solution(current.startingValue,
                                     if (isEven(current.currentValue))
                                       current.currentValue / 2
                                     else
                                       current.currentValue * 3 + 1, 
                                     current.length + 1))
          case Some(exponent) =>
            Solution(current.startingValue, 1, current.length + exponent - 1)
        }
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
