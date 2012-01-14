///////////////////////////////////////////////////////////////////////////////
//
// Tester for code-running functions.
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2011-12-16
//
///////////////////////////////////////////////////////////////////////////////

#include <cstdlib>
#include <cstdio>
#include <iostream>
#include <sstream>
#include "runner.h"
#include "rng.h"

using std::cout;
using std::endl;
using std::atoi;
using std::sprintf;
using std::ostringstream;

namespace run {

int comparator (const void * elem1, const void * elem2) {
  const double* a = static_cast<const double*>(elem1);
  const double* b = static_cast<const double*>(elem2);
  if (*a < *b)
    return -1;
  if (*b < *a)
    return 1;
  return 0;
}

// Oh noes, a global variable!
int count = 1000;

string SortNumbers() {
  double* numbers = new double[count];
  rng generator(-1);
  for (int i = 0; i < count; ++i)
    numbers[i] = generator.Random();
  ostringstream result;
  result << "First 10 unsorted: ";
  for (int i = 0; (i < count) && (i < 10); ++i)
    result << numbers[i] << " ";
  result << endl;
  qsort(numbers, count, sizeof(double), comparator);
  result << "First 10 sorted: ";
  for (int i = 0; (i < count) && (i < 10); ++i)
    result << numbers[i] << " ";
  result << endl;
  delete [] numbers;
  return result.str();
}

}

int main(int argc, char* argv[]) {
  using run::count;
  using run::PrintAndTime;
  using run::SortNumbers;

  if (argc != 2) {
    cout << "Expected 1 argument, got " << (argc - 1) << endl;
    exit(1);
  }
  int givenValue = atoi(argv[1]);
  if (givenValue) {
    count = givenValue;
  }
  ostringstream description;
  description << "Sorting " << count << " numbers";
  PrintAndTime(SortNumbers, description.str());
  return 0;
}
