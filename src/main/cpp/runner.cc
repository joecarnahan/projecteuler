///////////////////////////////////////////////////////////////////////////////
//
// Function for running arbitrary code and timing it.
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2011-12-16
//
///////////////////////////////////////////////////////////////////////////////

#include <iostream>
#include "runner.h"

using std::cout;
using std::endl;

namespace run {

void PrintAndTime(const toRun r, const string description) {
  cout << r() << endl;
  cout << description << " took " << TimeInSeconds(r) << "s." << endl;
}

time_t TimeInSeconds(const toRun r) {
  const int tries = 20;
  const time_t start = time(NULL);
  for (int i = 0; i < tries; ++i) {
    r();
  }
  return time(NULL) - start;
}

}
