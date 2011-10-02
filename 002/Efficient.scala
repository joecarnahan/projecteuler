/**
 * An improved solution to Problem 2 of Project Euler, where the
 * isEven() check is skipped in favor of just summing every third
 * Fibonacci number.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Efficient {

//sum=0
//a=1
//b=1
//c=a+b
//while c<limit
   //sum=sum+c
   //a=b+c
   //b=c+a
   //c=a+b

  def computeValuesUpTo(limit: Int): Int = {

    def computeValues(sum: Int, a: Int, b: Int, c: Int): Int =
      if (c >= limit)
        sum
      else {
        val newA = b + c
        val newB = c + newA
        val newC = newA + newB
        computeValues(sum + c, newA, newB, newC)
      }

    computeValues(0, 1, 1, 2)

  }


  def main(args: Array[String]) = 
    println(computeValuesUpTo(4000000))

}
