///////////////////////////////////////////////////////////////////////////////
//
// Interface for a tree-based collection that permits duplicate elements (a 
// multi-set).
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2011-12-16
//
///////////////////////////////////////////////////////////////////////////////

#if !defined( _TREE_JCC_ )
#define _TREE_JCC_

#include <string>

using std::string;

namespace tree {

template <class T>
class Tree {

 public:
  virtual ~Tree();

  virtual void   Add       (const T&) = 0;
  virtual void   Remove    (const T&) = 0;
  virtual void   RemoveAll (const T&) = 0;
  virtual Tree   Find      (const T&) const = 0;
  virtual string GetName   ()         const = 0;

};

}

#endif
