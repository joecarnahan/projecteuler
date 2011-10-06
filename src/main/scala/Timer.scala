/**
 * Utility for timing a block of code.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Timer {

  val tries = 10

  def timeInMilliseconds(codeToRun: () => Unit): Double = {

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
