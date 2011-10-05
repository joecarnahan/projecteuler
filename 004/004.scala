/**
 * My solution to problem 4 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _004 {

  val defaultLimit = 1000

  def findLargestPalindromeProductOfNumbersLessThan(limit: Long) = 12345
  // TODO Implement this

  def runOn(limit: Long) = {
    println(findLargestPalindromeProductOfNumbersLessThan(limit))
    println("This algorithm took an average of " + 
            Timer.timeInMilliseconds(() =>
              findLargestPalindromeProductOfNumbersLessThan(limit)) +
            "ms.")
  }

  def main(args: Array[String]) =
    if (args.length == 1)
      runOn(java.lang.Long.parseLong(args(0)))
    else
      runOn(defaultLimit)

}
