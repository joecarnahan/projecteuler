/**
 * Utility for running and timing a block of code.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Runner {

  val tries = 20

  def printAndTime(codeToRun: () => String, description: String) = {
    println(codeToRun())
    println(description + " took " + 
            timeInMilliseconds(codeToRun) + "ms.")
  }

  def timeInMilliseconds(codeToRun: () => Any): Double = {

    def time(index: Int, sum: Long): Double =
      if (index == tries)
        sum.asInstanceOf[Double] / tries
      else {
        val start = System.currentTimeMillis
        codeToRun()
        val finish = System.currentTimeMillis
        time(index + 1, sum + (finish - start))
      }

    time(0, 0L)
        
  }

}
