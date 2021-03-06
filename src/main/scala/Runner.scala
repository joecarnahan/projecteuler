/**
 * Utility for running and timing a block of code.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Runner {

  /**
   * Runs the given calculation, prints the result, and computes and
   * prints its average run time.
   *
   * @param args 
   *          Command-line arguments.  If these consist of a single
   *          value, then that value is passed to the given calculation.
   * @param parser
   *          Function for converting a string command-line argument into
   *          the type that is expected by the code to run
   * @param defaultArg
   *          The value to pass to the given calculation if a value is
   *          not given in the <code>args</code> parameter
   * @param codeToRun
   *          The code to run
   * @param description
   *          The name to use for this code when reporting its average
   *          running time
   */
  def run[T](args: Array[String], 
             parser: String => T,
             defaultArg: T,
             codeToRun: T => String, 
             description: String) = 
    if (args.length == 1)
      printAndTime(() => codeToRun(parser(args(0))), description)
    else
      printAndTime(() => codeToRun(defaultArg), description)

  /**
   * Prints out the results of the given code along with its average
   * running time.
   *
   * @param codeToRun
   *          The code to run
   * @param description
   *          The name to use for this code when reporting its average
   *          running time.
   */
  def printAndTime(codeToRun: () => String, description: String) = {
    println(codeToRun())
    printTime(codeToRun, description)
  }

  /**
   * Prints out the average running time of the given code.
   *
   * @param codeToRun
   *          The code to run
   * @param description
   *          The name to use for this code when reporting its average
   *          running time.
   */
  def printTime(codeToRun: () => String, description: String) = {
    println(description + " took " + 
            timeInMilliseconds(codeToRun) + "ms.")
  }

  /**
   * The number of times to run a function when computing its average
   * running time.
   */
  val tries = 20

  /**
   * Runs the given code repeatedly and returns its average running time.
   *
   * @param codeToRun
   *          the code to run
   * @return the average time required to run the given function
   */
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
