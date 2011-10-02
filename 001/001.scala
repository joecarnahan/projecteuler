object _001 {

  def isMultipleOf(x: Int, multipleOf: Int): Boolean = ((x % multipleOf) == 0)

  def isMultipleOf(x: Int, multiplesOf: Seq[Int]): Boolean =
    multiplesOf.exists((y: Int) => ((x % y) == 0))

  def sumOfMultiplesLessThan(limit: Int, multiplesOf: Seq[Int]): Int = {

    def sumOfMultiplesLessThanRec(curr: Int, acc: Int): Int = 
      if (curr < limit)
          sumOfMultiplesLessThanRec(curr + 1, 
                                    if (isMultipleOf(curr, multiplesOf))
                                      acc
                                    else
                                      acc + curr)
      else
        acc

    sumOfMultiplesLessThanRec(0, 0)
  }

  def main(args: Array[String]) = println(sumOfMultiplesLessThan(1000, List(3,5)))

}
