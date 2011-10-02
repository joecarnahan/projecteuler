/**
 * My solution to problem 2 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Naive {

  def isEven(toTest: Int) = ((toTest % 2) == 0)

  def computeValuesUpTo(limit: Int): Int = {

    def computeValues(curr: Int, prev: Int, sum: Int): Int =
      if (curr >= limit)
        sum
      else
        computeValues(curr + prev, curr,
                      if (isEven(curr)) sum + curr else sum)
                        

    computeValues(1, 0, 0)

  }


  def main(args: Array[String]) = 
    println(computeValuesUpTo(4000000))

}
