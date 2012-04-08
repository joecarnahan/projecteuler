/**
 * My solution to problem 8 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _008 {

  /**
   * Searches the digits in the given string for the sequence of digits
   * with the largest product.
   * <p>
   * The key optimization here is that we won't find a sequence with a 
   * larger product unless the integer that we add to the sequence is
   * larger than the integer that it is replacing.
   *
   * @param numberString
   *          the string to search, which is assumed to only contain
   *          characters between '0' and '9'
   * @param sequenceSize
   *          the size of sequence to consider
   * @return the largest product of any sequence of the given length in
   *         the given string
   */
  def getLargestProductOfDigits(numberString: String, sequenceSize: Int): Int = {
    val numberList = numberString.map((c: Char) => 
      java.lang.Character.digit(c, 10)).toList
    Common.findLargestProduct(numberList, sequenceSize).product
  }

  /**
   * The default string is split across multiple lines for readability,
   * but all whitespace is stripped from it using 
   * <code>String.replaceAll("\\s", "")</code> before this string is actually
   * used.
   */
  val defaultString = """73167176531330624919225119674426574742355349194934
                        |96983520312774506326239578318016984801869478851843
                        |85861560789112949495459501737958331952853208805511
                        |12540698747158523863050715693290963295227443043557
                        |66896648950445244523161731856403098711121722383113
                        |62229893423380308135336276614282806444486645238749
                        |30358907296290491560440772390713810515859307960866
                        |70172427121883998797908792274921901699720888093776
                        |65727333001053367881220235421809751254540594752243
                        |52584907711670556013604839586446706324415722155397
                        |53697817977846174064955149290862569321978468622482
                        |83972241375657056057490261407972968652414535100474
                        |82166370484403199890008895243450658541227588666881
                        |16427171479924442928230863465674813919123162824586
                        |17866458359124566529476545682848912883142607690042
                        |24219022671055626321111109370544217506941658960408
                        |07198403850962455444362981230987879927244284909188
                        |84580156166097919133875499200524063689912560717606
                        |05886116467109405077541002256983155200055935729725
                        |71636269561882670428252483600823257530420752963450"""

  /**
   * The sequence size to examine.  For this problem, the sequence size should
   * be 5, but this algorithm should work for any sequence size smaller than
   * or equal to the size of the string that is being considered.
   */
  val numDigits = 5

  def main(args: Array[String]) =
    Runner.run[String](args, identity _, 
                       defaultString.replaceAll("\\s", ""),
                       getLargestProductOfDigits(_, numDigits).toString,
                       "Solution to problem 8")

}
