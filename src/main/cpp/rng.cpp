/* -------------------------------------------------------------------------
 * This is a C++ class for random number generation.  The use of this 
 * library is recommended as a replacement for the ANSI C rand() and srand()
 * functions, particularly in simulation applications where the statistical
 * 'goodness' of the random number generator is important.  This class is
 * based on the "rngs" library that was developed by Steve Park and Dave
 * Geyer.
 *
 * The generator used in this library is a so-called 'Lehmer random number
 * generator' which returns a pseudo-random number uniformly distributed
 * 0.0 and 1.0.  The period is (m - 1) where m = 2,147,483,647 and the
 * smallest and largest possible values are (1 / m) and 1 - (1 / m)
 * respectively.  For more details see:
 * 
 *       "Random Number Generators: Good Ones Are Hard To Find"
 *                   Steve Park and Keith Miller
 *              Communications of the ACM, October 1988
 *
 * Note that as of 7-11-90 the multiplier used in this library has changed
 * from the previous "minimal standard" 16807 to a new value of 48271.  To
 * use this library in its old (16807) form change the constants MULTIPLIER
 * and CHECK as indicated in the comments.
 *
 * Name              : rng.cc
 * Author            : Joseph Carnahan <carnahan@virginia.edu>
 *                     Based on original C code by Steve Park & Dave Geyer
 * Language          : C++
 * Latest Revision   : 01-30-03
 * ------------------------------------------------------------------------- 
 */

#include "rng.h"
#include <ctime>
#include <iostream>

using std::time;
using std::cout;
using std::cin;
using std::endl;
using namespace run;

rng::rng()
/* ---------------------------------------------------------------------
 * Default constructor.  Sets the seed to the default value.
 * ---------------------------------------------------------------------   
 */
{
  seed = DEFAULT;
}


rng::rng(long x)
/* ---------------------------------------------------------------------
 * Seed constructor - Takes a long integer "x" as a parameter.
 * If "x" is positive, "x" is used as the seed.  If "x" is 
 * negative, uses the system time as the seed.  If "x" is
 * zero, interactively prompts the user for a seed.
 * ---------------------------------------------------------------------   
 */
{
  PutSeed(x);
}


double rng::Random(void)
/* ---------------------------------------------------------------------
 * Random is a Lehmer generator that returns a pseudo-random real number
 * uniformly distributed between 0.0 and 1.0.  The period is (m - 1)
 * where m = 2,147,483,647 amd the smallest and largest possible values
 * are (1 / m) and 1 - (1 / m) respectively.                             
 * ---------------------------------------------------------------------   
 */
{
  const long Q = MODULUS / MULTIPLIER;
  const long R = MODULUS % MULTIPLIER;
        long t;

  t = MULTIPLIER * (seed % Q) - R * (seed / Q);
  if (t > 0) 
    seed = t;
  else 
    seed = t + MODULUS;
  return ((double) seed / MODULUS);
}


void rng::PutSeed(long x)
/* -------------------------------------------------------------------
 * Use this (optional) procedure to initialize or reset the state of
 * the random number generator according to the following conventions:
 *    if x > 0 then x is the initial seed (unless too large)
 *    if x < 0 then the initial seed is obtained from the system clock
 *    if x = 0 then the initial seed is to be supplied interactively
 * --------------------------------------------------------------------
 */
{
  char ok = 0;

  if (x > 0L)
    x = x % MODULUS;                          /* correct if x is too large  */
  if (x < 0L)                                 
    x = ((unsigned long) time((time_t *) NULL)) % MODULUS;              
  if (x == 0L)                                
    while (!ok) {
      cout << endl << "Enter a positive integer seed (9 digits or less): ";
      cin >> x;
      ok = (0L < x) && (x < MODULUS);
      if (!ok)
        cout << endl << "Input out of range ... try again" << endl;
    }
  seed = x;
}


void rng::GetSeed(long &x)
/* --------------------------------------------------------------------
 * Use this (optional) procedure to get the current state of the random
 * number generator.                    
 * --------------------------------------------------------------------
 */
{
  x = seed;
}


void rng::TestRandom(void)
/* -------------------------------------------------------------------
 * Use this (optional) procedure to test for a correct implementation.
 * -------------------------------------------------------------------    
 */
{
  long   i;
  long   x;
  double u;

  PutSeed(1);                                /* set initial state to 1 */
  for(i = 0; i < 10000; i++)
    u = Random();
  GetSeed(x);                               /* get the new state      */
  if (x == CHECK) 
    cout << endl << "The implementation of Random is correct." << endl;
  else
    cout << endl << "ERROR: The implementation of Random is not correct!" << endl;
}
