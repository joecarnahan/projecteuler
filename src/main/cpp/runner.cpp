///////////////////////////////////////////////////////////////////////////////
//
// Function for running arbitrary code and timing it.
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2011-12-12
//
///////////////////////////////////////////////////////////////////////////////

#include <iostream>
#include "runner.h"

using std::cout;
using std::endl;

void printAndTime(toRun r, string description) {
  cout << r() << endl;
  cout << description << " took " << timeInSeconds(r) << "ms." << endl;
}

time_t timeInSeconds(toRun r) {
  const int tries = 20;
  const time_t start = time(NULL);
  for (int i = 0; i < tries; i++) {
    r();
  }
  return time(NULL) - start;
}

