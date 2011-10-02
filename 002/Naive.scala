/**
 * My solution to problem 2 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Naive {

  def computeValuesUpTo(limit: Int): Int = {

    def computeValues(curr: Int, prev: Int, sum: Int): Int =
      if (curr >= limit)
        sum
      else
        computeValues(curr + prev, curr,
                      if ((curr % 2) == 0) sum + curr else sum)
                        

    computeValues(1, 0, 0)

  }


  def main(args: Array[String]) = 
    println(computeValuesUpTo(4000000))

}
