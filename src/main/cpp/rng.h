/* -------------------------------------------------------------------------  
 * Name            : rng.h  (header file for the random number generator
 *                   class) 
 * Author          : Joseph Carnahan <carnahan@virginia.edu>
 *                   Based on orginal C code by Steve Park & Dave Geyer  
 * Language        : C++
 * Latest Revision : 2011-12-16
 * ------------------------------------------------------------------------- 
 */

#if !defined( _RNG_ )
#define PROJECTEULER_RNG_H_

namespace run {

class rng {

 public:

  // Default constructor - Uses a default seed value (defined below).
  rng();

  // Seed constructor - Takes a long integer "x" as a parameter.
  // If "x" is positive, "x" is used as the seed.  If "x" is 
  // negative, uses the system time as the seed.  If "x" is
  // zero, interactively prompts the user for a seed.
  explicit rng(const long x);

  // Returns a double-precision floating point number, uniformly
  // distributed between 0.0 and 1.0.
  double Random(void);

  // Returns the value of the current seed.
  long GetSeed() const;

  // Sets the seed for the random number generator.  
  // If "x" is positive, "x" is used as the seed.  If "x" is 
  // negative, uses the system time as the seed.  If "x" is
  // zero, interactively prompts the user for a seed.
  void PutSeed(const long x);

  // A function for verifying the implementation of this
  // random number generator.
  static void TestRandom(void);

 private:

  // Disallow copy and assignment (what would that even mean here?)
  rng(const rng&);
  void operator=(const rng&);

  // The state of the random number generator
  long seed_;

  // Internal constants
  static const long kModulus    = 2147483647;
  static const long kMultiplier = 48271;
  static const long kCheck      = 399268537;
  static const long kDefault    = 123456789;

};

}

#endif
