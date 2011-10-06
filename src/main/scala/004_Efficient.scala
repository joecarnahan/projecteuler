/**
 * A more efficient solution to problem 4 of Project Euler.
 *
 * This solution takes advantage of the insight that palindromes are
 * products of 11.  This solution also interrupts each iteration so
 * that it stops checking once it's certain that a given pair of 
 * numbers is small enough that it can't possibly have a product bigger
 * than the current largest palindrome.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _004_Efficient {

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

    def findInitialB(a: Long): Long =
      if ((a % 11) == 0)
        limit - 1
      else {
        def search(b: Long): Long =
          if ((b % 11) == 0)
            b
          else
            search(b - 1)
        search(limit - 2)
      }

    def findInitialStepB(b: Long): Long = 
      if ((b % 11) == 0)
        11
      else
        1

    def search(a: Long, b: Long, stepB: Long, largest: Long): Long =
      if (a <= 1)
        largest
      else if (b < a) {
        val newA = a - 1
        val newB = findInitialB(newA)
        search(newA, newB, findInitialStepB(newB), largest)
      }
      else {
        val product = a * b
        if (product <= largest) {
          val newA = a - 1
          val newB = findInitialB(newA)
          search(newA, newB, findInitialStepB(newB), largest)
        }
        else if (isPalindrome(product)) {
          val newA = a - 1
          val newB = findInitialB(newA)
          search(newA, newB, findInitialStepB(newB), product)
        }
        else 
          search(a, b - stepB, stepB, largest)
      }

    val a = limit - 1
    val b = findInitialB(a)
    search(a, b, findInitialStepB(b), 1)
  }

  def runOn(limit: Long) = {
    println(findLargestPalindromeProductOfNumbersLessThan(limit))
    println("Efficient solution to problem 4 took " +
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
