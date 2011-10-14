/**
 * My solution to problem 9 of Project Euler.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object _009 {

  def getPythagoreanTriplet(value: Long): List[Int] = {

    def sum = value.toInt

    def search(a: Int, b: Int, c: Int): List[Int] =
      if ((a * a) + (b * b) == (c * c))
        List(a, b, c)
      else if (a == (b - 1)) {
        val newB = b + 1
        if (newB > (sum / 3)) 
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

  def getPythagoreanTripletOrError(value: Long): List[Int] = {
    val result = getPythagoreanTriplet(value)
    if (result.isEmpty)
      sys.error("No Pythagorean triplet sums to " + value)
    else
      result
  }

  val defaultValue = 1000L

  def main(args: Array[String]) =
    Runner.runLong(args, defaultValue,
                   getPythagoreanTripletOrError(_).product.toString,
                   "Solution to problem 9")

}
