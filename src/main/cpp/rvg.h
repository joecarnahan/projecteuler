/* -------------------------------------------------------------- 
 * Name            : rvg.h (header file for the random variate
 *                   generator class)
 * Author          : Joseph Carnahan <carnahan@virginia.edu>
 *                   Based on original C code by Steve Park & 
 *                   Dave Geyer
 * Language        : C++
 * Latest Revision : 01-30-03
 * -------------------------------------------------------------- 
 */

#if !defined( _RVG_ )
#define _RVG_

#include "rng.h"

namespace run {

class rvg {

  public:

    // Default constructor - Uses a default seed value (defined in
    // the "rng" class).
    rvg();

    // Seed constructor - Takes a long integer "x" as a parameter.
    // If "x" is positive, "x" is used as the seed.  If "x" is 
    // negative, uses the system time as the seed.  If "x" is
    // zero, interactively prompts the user for a seed.
    explicit rvg(long x);

    // Stores the value of the current seed into the location
    // pointed to by *x.
    void GetSeed(long &x);

    // Sets the seed for the random number generator.  
    // If "x" is positive, "x" is used as the seed.  If "x" is 
    // negative, uses the system time as the seed.  If "x" is
    // zero, interactively prompts the user for a seed.
    void PutSeed(long x);

    // Discrete random variates
    long Bernoulli(double p);
    long Binomial(long n, double p);
    long Equilikely(long a, long b);
    long Geometric(double p);
    long Pascal(long n, double p);
    long Poisson(double m);
  
    // Continuous random variates
    double Uniform(double a, double b);
    double Exponential(double m);
    double Erlang(long n, double b);
    double Normal(double m, double s);
    double Lognormal(double a, double b);
    double Chisquare(long n);
    double Student(long n);

  private:

    // The random number generator, used to support these
    // random variates
    rng *generator;

  };

}

#endif



