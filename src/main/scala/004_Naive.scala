/**
 * My initial solution to problem 4 of Project Euler.
 *
 * This just checks every product of numbers between 1 and 999 inclusive
 * to see if it's a palindrome and keeps the largest one.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _004_Naive {

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
      if (a <= 1)
        largest
      else if (b < a)
        search(a - 1, limit - 1, largest)
      else {
        val product = a * b
        if (product <= largest)
          search(a - 1, limit - 1, largest)
        else if (isPalindrome(product))
          search(a - 1, limit - 1, product)
        else
          search(a, b - 1, largest)
      }

    search(limit - 1, limit - 1, 1)
  }

  val defaultLimit = 1000

  def main(args: Array[String]) =
    Runner.run[Long](args, java.lang.Long.parseLong _, defaultLimit,
                     findLargestPalindromeProductOfNumbersLessThan(_).toString,
                     "Naive solution to problem 4")

}
