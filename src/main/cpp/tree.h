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
      virtual void   add       (const T&) = 0;
      virtual void   remove    (const T&) = 0;
      virtual void   removeAll (const T&) = 0;
      virtual Tree   find      (const T&) = 0;
      virtual string getName   ()         = 0;

  };

}

#endif
