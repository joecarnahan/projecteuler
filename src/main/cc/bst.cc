///////////////////////////////////////////////////////////////////////////////
//
// Implmentation of a binary search tree.
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2011-12-16
//
///////////////////////////////////////////////////////////////////////////////

#include "bst.h"

namespace tree {

// Creates a binary search tree with the assumption that operator< and
// operator== are defined on type T.
template <class T>
BST<T>::BST() {
  // TODO
}

// Creates a binary search tree with the given implementations of "less
// than" and "equals".
template <class T>
BST<T>::BST(const binary_function<T, T, bool>* lessThan, 
            const binary_function<T, T, bool>* equals) {
  // TODO
}

template <class T>
BST<T>::~BST() {
  // TODO
}

template <class T>
void BST<T>::Add (const T& toRemove) {
  // TODO
}

template <class T>
void BST<T>::Remove (const T& toRemove) {
  // TODO
}

template <class T>
void BST<T>::RemoveAll (const T& toRemove) {
  // TODO
}

template <class T>
void RemoveImpl(const T& toRemove, const bool removeAll) {
  // TODO
}

template <class T>
Tree<T> BST<T>::Find (const T& toFind) const {
  // TODO
}

}
