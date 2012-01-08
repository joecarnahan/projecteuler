///////////////////////////////////////////////////////////////////////////////
//
// Header file for a binary search tree.
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2011-12-16
//
///////////////////////////////////////////////////////////////////////////////

#if !defined( _BST_JCC_ )
#define _BST_JCC_

#include <functional>
#include "tree.h"

using std::binary_function;

namespace tree {

template <class T>
class BST : public Tree<T> {

 public:

  // Creates a binary search tree with the assumption that operator< and
  // operator== are defined on type T.
  BST();

  // Creates a binary search tree with the given implementations of "less
  // than" and "equals".
  BST(const binary_function<T, T, bool>* lessThan, 
      const binary_function<T, T, bool>* equals);

  ~BST();

  void    Add       (const T&);
  void    Remove    (const T&);
  void    RemoveAll (const T&);
  Tree<T> Find      (const T&) const;

  inline string GetName () const { return "Binary search tree"; }

 private:

  // Disallow copying and assignment, at least until they are implemented.
  BST(const BST&);
  void operator=(const BST&);

  void RemoveImpl(const T&, bool);

  T*   value_;
  BST* left_;
  BST* right_;
  binary_function<T, T, bool>* lessThan_;
  binary_function<T, T, bool>* equals_;

};

}

#endif
