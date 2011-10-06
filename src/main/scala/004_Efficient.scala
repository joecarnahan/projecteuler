/**
 * A more efficient solution to problem 4 of Project Euler.
 *
 * This solution takes advantage of the insight that large 
 * palindromes are products of 11.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _004_Efficient {

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

  val defaultLimit = 1000

  def main(args: Array[String]) =
    Runner.run(args, defaultLimit,
               findLargestPalindromeProductOfNumbersLessThan(_).toString,
               "Efficient solution to problem 4")

}
