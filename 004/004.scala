/**
 * My solution to problem 4 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _004 {

  val defaultLimit = 1000

  def isPalindrome(toCheck: String): Boolean = 
    if (toCheck.size < 2)
      true
    else if (toCheck(0) == toCheck((toCheck.size - 1)))
      isPalindrome(toCheck.substring(1, toCheck.size - 1))
    else
      false

  // def isPalindrome(toCheck: Long): Boolean = isPalindrome(toCheck.toString)
  // debug
  def isPalindrome(toCheck: Long): Boolean = {
    val s = toCheck.toString
    if (isPalindrome(s)) {
      println(s + " is a palindrome")
      true
    }
    else {
      println(s + " is not a palindrome")
      false
    }
  }
  // enddebug

  def findLargestPalindromeProductOfNumbersLessThan(limit: Long) = {
    
    def search(a: Long, b: Long): Long =
      // debug
      {
        println("Trying " + a.toString + " and " + b.toString)
      // enddebug
      if (isPalindrome(a * b))
        a * b
      else if (a == b)
        search(a - 1, limit - 1)
      else
        search(a, b - 1)
      // debug
      }
      // enddebug

    search(limit - 1, limit - 1)
  }

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
