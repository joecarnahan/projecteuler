///////////////////////////////////////////////////////////////////////////////
//
// Function for running arbitrary code and timing it.
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2012-01-14
//
///////////////////////////////////////////////////////////////////////////////

#include <iostream>
#include <iomanip>
#include "runner.h"

using std::cout;
using std::endl;
using std::setprecision;

namespace run {

void PrintAndTime(const toRun r, const string description) {
  cout << r() << endl
       << description << " took " << setprecision(3) << TimeInSeconds(r) 
       << "s." << endl;
}

double TimeInSeconds(const toRun r) {
  const int tries = 100;
  const time_t start = time(NULL);
  for (int i = 0; i < tries; ++i) {
    r();
  }
  return (time(NULL) - start) / (double) tries;
}

}
