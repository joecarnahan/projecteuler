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

      void    add       (const T&);
      void    remove    (const T&);
      void    removeAll (const T&);
      Tree<T> find      (const T&);

      inline string getName () { return "Binary search tree"; }

      ~BST();

    private:
      T*   _value;
      BST* _left;
      BST* _right;
      binary_function<T, T, bool>* _lessThan;
      binary_function<T, T, bool>* _equals;

      void removeImpl(const T&, bool);

      // Disallow copying and assignment, at least until they are implemented.
      BST(const BST&);
      void operator=(const BST&);
  };

}

#endif
