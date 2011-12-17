/* -------------------------------------------------------------- 
 * Name            : rvg.h (header file for the random variate
 *                   generator class)
 * Author          : Joseph Carnahan <carnahan@virginia.edu>
 *                   Based on original C code by Steve Park & 
 *                   Dave Geyer
 * Language        : C++
 * Latest Revision : 2011-12-16
 * -------------------------------------------------------------- 
 */

#if !defined( _RVG_ )
#define PROJECTEULER_RVG_H_

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
  explicit rvg(const long x);

  ~rvg();

  // Gets the value of the current seed.
  long GetSeed() const;

  // Sets the seed for the random number generator.  
  // If "x" is positive, "x" is used as the seed.  If "x" is 
  // negative, uses the system time as the seed.  If "x" is
  // zero, interactively prompts the user for a seed.
  void PutSeed(const long x);

  // Discrete random variates
  long Bernoulli(const double p);
  long Binomial(const long n, const double p);
  long Equilikely(const long a, const long b);
  long Geometric(const double p);
  long Pascal(const long n, const double p);
  long Poisson(const double m);

  // Continuous random variates
  double Uniform(const double a, const double b);
  double Exponential(const double m);
  double Erlang(const long n, const double b);
  double Normal(const double m, const double s);
  double Lognormal(const double a, const double b);
  double Chisquare(const long n);
  double Student(const long n);

 private:

  // Disallow copy and assignment (what would that even mean here?)
  rvg(const rvg&);
  void operator=(const rvg&);

  // The random number generator, used to support these
  // random variates
  rng* generator_;
};

}

#endif



