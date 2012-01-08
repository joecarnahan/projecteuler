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
 * use this library in its old (16807) form change the constants kMultiplier
 * and kCheck as indicated in the comments.
 *
 * Name              : rng.cc
 * Author            : Joseph Carnahan <carnahan@virginia.edu>
 *                     Based on original C code by Steve Park & Dave Geyer
 * Language          : C++
 * Latest Revision   : 2011-12-16
 * ------------------------------------------------------------------------- 
 */

#include <ctime>
#include <iostream>
#include "rng.h"

using std::time;
using std::cout;
using std::cin;
using std::endl;

namespace run {

rng::rng()
/* ---------------------------------------------------------------------
 * Default constructor.  Sets the seed to the default value.
 * ---------------------------------------------------------------------   
 */
{
  seed_ = kDefault;
}


rng::rng(const long x)
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
  const long Q = kModulus / kMultiplier;
  const long R = kModulus % kMultiplier;
  const long t = kMultiplier * (seed_ % Q) - R * (seed_ / Q);
  if (t > 0) 
    seed_ = t;
  else 
    seed_ = t + kModulus;
  return ((double) seed_ / kModulus);
}


void rng::PutSeed(const long x)
/* -------------------------------------------------------------------
 * Use this (optional) procedure to initialize or reset the state of
 * the random number generator according to the following conventions:
 *    if x > 0 then x is the initial seed (unless too large)
 *    if x < 0 then the initial seed is obtained from the system clock
 *    if x = 0 then the initial seed is to be supplied interactively
 * --------------------------------------------------------------------
 */
{
  long seedToUse = x;
  if (seedToUse > 0L)
    seedToUse = seedToUse % kModulus;  /* correct if x is too large  */
  if (seedToUse < 0L)                                 
    seedToUse = ((unsigned long) time((time_t *) NULL)) % kModulus;              
  if (seedToUse == 0L) {
    bool ok = false;
    while (!ok) {
      cout << endl << "Enter a positive integer seed (9 digits or less): ";
      cin >> seedToUse;
      ok = (0L < seedToUse) && (seedToUse < kModulus);
      if (!ok)
        cout << endl << "Input out of range ... try again" << endl;
    }
  }
  seed_ = seedToUse;
}


long rng::GetSeed() const
/* --------------------------------------------------------------------
 * Use this (optional) procedure to get the current state of the random
 * number generator.                    
 * --------------------------------------------------------------------
 */
{
  return seed_;
}


void rng::TestRandom(void)
/* -------------------------------------------------------------------
 * Use this (optional) procedure to test for a correct implementation.
 * -------------------------------------------------------------------    
 */
{
  rng generator(1);
  for(int i = 0; i < 10000; ++i)
    generator.Random();
  if (generator.GetSeed() == kCheck) 
    cout << endl << "The implementation of Random is correct." << endl;
  else
    cout << endl << "ERROR: The implementation of Random is not correct!" << endl;
}

}
