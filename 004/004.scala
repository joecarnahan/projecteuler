/**
 * My solution to problem 4 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _004 {

  val defaultLimit = 1000

  // Thoughts:
  //  - Palindromes must have even numbers of digits
  //     - How can we predict how many digits there will be?
  //  - Want to start trying numbers close to 1000 and work down
  //  - How can we check if a product is a palindrome?
  //     - String conversion
  //     - Other ideas?

  //def isPalindrome = 

  //def findLargestPalindromeProductOfNumbersLessThan(limit: Long) = 
    
    //def search(a: Long, b: Long): Long =
      //if (

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
