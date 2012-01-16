///////////////////////////////////////////////////////////////////////////////
//
// Interface for a tree-based collection that permits duplicate elements (a 
// multi-set).
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2011-12-16
//
///////////////////////////////////////////////////////////////////////////////

#include <cstdlib>
#include <cstring>
#include <iostream>
#include "tree.h"
#include "bst.h"
#include "rvg.h"

using std::cout;
using std::endl;

namespace tree {

// Oh noes, a global variable!
int count = 1000;

// Fills in the given array with random integers between "count" and "-count".
void FillRandom(int* toFill, int size) {
  run::rvg generator(-1);
  for (int i = 0; i < size; i++) {
    toFill[i] = generator.Equilikely(-count, count);
  }
}

int IntComparator (const void * elem1, const void * elem2) {
  const int* a = static_cast<const int*>(elem1);
  const int* b = static_cast<const int*>(elem2);
  if (*a < *b)
    return -1;
  if (*b < *a)
    return 1;
  return 0;
}

// Fills in the second array with a sorted copy of the first array.
void FillSorted(int* toSort, int* sortedCopy, int size) {
  std::memcpy(sortedCopy, toSort, size * sizeof(int));
  std::qsort(sortedCopy, size, sizeof(int), IntComparator);
}

// Fills in the second array with a reversed copy of the 
void FillReversed(int* toReverse, int* reversedCopy, int size) {
  int j = size - 1;
  for (int i = 0; i < size; ++i) {
    reversedCopy[j] = toReverse[i];
    --j;
  }
}

void TestSorted(const int* array, const int size) {
  const int secondToLast = size - 1;
  for (int i = 0; i < secondToLast; ++i) {
    if (array[i] > array[i + 1]) {
      std::cerr << "TestSorted failed: Value " << array[i]
                << " came before value " << array[i + 1] << endl;
      exit(2);
    }
  }
}

void TestReversed(const int* array, const int size) {
  const int secondToLast = size - 1;
  for (int i = 0; i < secondToLast; ++i) {
    if (array[i] < array[i + 1]) {
      std::cerr << "TestReversed failed: Value " << array[i]
                << " came before value " << array[i + 1] << endl;
      exit(3);
    }
  }
}

// Code for testing the Fill-functions.
void TestHelpers() {
  int* unsorted = new int[count];
  int* sorted = new int[count];
  int* reversed = new int[count];
  FillRandom(unsorted, count);
  FillSorted(unsorted, sorted, count);
  FillReversed(sorted, reversed, count);
  TestSorted(sorted, count);
  TestReversed(reversed, count);
}

template <class T>
void TestTree(Tree<T>& tree) {

  // TODO 
  cout << "This is where we would test the tree." << endl;
}

void TestAll() {
  TestHelpers();
  // TODO Add AVL and red-black trees here when they're implemented
  const int numTrees = 1;
  Tree<int>* trees[numTrees];
  trees[0] = new BST<int>();
  for (int i = 0; i < numTrees; i++) {
    TestTree(*(trees[i]));
  }
}

}

int main(int argc, char* argv[]) {

  using tree::count;
  using tree::TestAll;

  if (argc > 2) {
    cout << "Expected 1 argument, got " << (argc - 1) << endl;
    return 1;
  }
  int givenValue = 0;
  if (argc == 2) {
    givenValue = atoi(argv[1]);
  }
  if (givenValue) {
    count = givenValue;
  }

  TestAll();
  return 0;
}
