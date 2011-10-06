/**
 * My initial solution to problem 4 of Project Euler.
 *
 * This just checks every product of numbers between 1 and 999 inclusive
 * to see if it's a palindrome and keeps the largest one.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _004_Naive {

  val defaultLimit = 1000

  def isPalindrome(toCheck: String): Boolean = 
    if (toCheck.size < 2)
      true
    else if (toCheck(0) == toCheck((toCheck.size - 1)))
      isPalindrome(toCheck.substring(1, toCheck.size - 1))
    else
      false

  def isPalindrome(toCheck: Long): Boolean = isPalindrome(toCheck.toString)

  def findLargestPalindromeProductOfNumbersLessThan(limit: Long) = {

    def search(a: Long, b: Long, largest: Long): Long =
      if (a == limit)
        largest
      else if (b == limit)
        search(a + 1, a + 1, largest)
      else {
        val product = a * b
        if (isPalindrome(product) && (product > largest))
          search(a, b + 1, product)
        else
          search(a, b + 1, largest)
      }

    search(1, 1, 1)
  }

  def runOn(limit: Long) = {
    println(findLargestPalindromeProductOfNumbersLessThan(limit))
    println("Naive algorithm took an average of " + 
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
