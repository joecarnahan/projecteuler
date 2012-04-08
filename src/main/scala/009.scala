/**
 * My solution to problem 9 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _009 {

  /**
   * Tests if the given triple of numbers is a 
   * Pythagorean triple.
   *
   * @param a 
   *          first number to test
   * @param b 
   *          second number to test
   * @param c 
   *          third number to test
   * @return <code>true</code> if and only if (a*a) + (b*b) = (c*c)
   */
  def isTriple(a: Int, b: Int, c: Int): Boolean = 
    ((a * a) + (b * b)) == (c * c) 

  /**
   * Searches for a Pythagorean triplet that sums to the given value.
   * <p>
   * Starts with <code>a = 1</code>, <code>b = 2</code>, and
   * <code>c = value - (a + b)</code>.  Then, and it increments
   * <code>a</code> until it reaches <code>b</code>.  At that point, it
   * increments <code>b</code> and starts over again with <code>a = 1</code>.
   *
   * @param value
   *          the number to which the three numbers in the Pythagorean
   *          triple should sum
   * @return a list of three integers consisting of a Pythagorean
   *         triple that sums to <code>value</code>, or an empty list if
   *         no Pythagorean triple sums to <code>value</code>
   */
  def getPythagoreanTriplet(value: Long): List[Int] = {

    def sum = value.toInt

    def search(a: Int, b: Int, c: Int): List[Int] =
      if (isTriple(a, b, c))
        List(a, b, c)
      else if ((a >= (b - 1)) || (a >= c)) {
        val newB = b + 1
        if (newB > (sum / 2)) 
          List[Int]()          // No Pythagorean triple exists
        else {
          val newA = 1
          search(newA, newB, sum - (newA + newB))
        }
      }
      else {
        val newA = a + 1
        search(newA, b, sum - (newA + b))
      }
    
    search(1, 2, sum - (1 + 2))

  }

  /**
   * Searches for a Pythagorean triplet that sums to the given value.
   *
   * @param value
   *          the number to which the three numbers in the Pythagorean
   *          triple should sum
   * @return a list of three integers consisting of a Pythagorean
   *         triple that sums to <code>value</code> 
   * @throws RuntimeException
   *           if no Pythagorean triple exists that sums to 
   *           <code>value</code>
   */
  def getPythagoreanTripletOrError(value: Long): List[Int] = {
    val result = getPythagoreanTriplet(value)
    if (result.isEmpty)
      sys.error("No Pythagorean triplet sums to " + value)
    else
      result
  }

  /**
   * Brute-force method for finding Pythagorean triplets.  Used for
   * debugging this algorithm.
   *
   * @param n
   *          the number of triplets to get
   */
  def getNTriplets(n: Int): List[List[Int]] = {

    def increment(a: Int, b: Int, c: Int): (Int, Int, Int) = 
      if (a == (b - 1))
        if (b == (c - 1))
          (1, 2, c + 1)
        else
          (1, b + 1, c)
      else
        (a + 1, b, c)
    
    def search(a: Int, b: Int, c: Int, result: List[List[Int]]): List[List[Int]] =
      if (result.size == n)
        result
      else {
        val (newA, newB, newC) = increment(a, b, c)
        if (isTriple(a, b, c))
          search(newA, newB, newC, List(a, b, c) :: result)
        else
          search(newA, newB, newC, result)
      }
      
    search(1, 2, 3, List[List[Int]]())

  }

  val defaultValue = 1000L

  def main(args: Array[String]) =
    Runner.run[Long](args, java.lang.Long.parseLong _, defaultValue,
                     getPythagoreanTripletOrError(_).product.toString,
                     "Solution to problem 9")

}
