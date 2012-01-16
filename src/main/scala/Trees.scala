/**
 * Implementations of tree-based data structures.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Tree {

  /**
   * Creates an empty binary search tree.
   *
   * @param ordering
   *          the ordering to use on the elements
   * @return an empty binary search tree
   */
  def binarySearchTree[A](implicit ordering: Ordering[A]): Tree[A] = 
    new EmptyBST[A](ordering)

  /**
   * Creates an empty AVL tree.
   *
   * @param ordering
   *          the ordering to use on the elements
   * @return an empty AVL tree
   */
  def avlTree[A](implicit ordering: Ordering[A]): Tree[A] =
    new EmptyAVL[A](ordering)

  /**
   * Creates an empty red-black tree.
   *
   * @param ordering
   *          the ordering to use on the elements
   * @return an empty red-black tree
   */
  def redBlackTree[A](implicit ordering: Ordering[A]): Tree[A] = 
    new EmptyRBT[A](ordering)

}

/**
 * Interface for persistent tree-based data structures.
 */
trait Tree[A] {

  /**
   * Adds the given element to the tree.
   *
   * @param toAdd
   *          the element to add
   * @return the new tree with the given element added to it
   */
  def add(toAdd: A): Tree[A]

  /**
   * Deletes the given element from the tree.
   *
   * @param toRemove
   *          the element to remove
   * @return the new tree with one instance of the given element removed from
   *         it, or None if the given element is not in the tree
   */
  def remove(toRemove: A): Option[Tree[A]]

  /**
   * Deletes all instances of the given element from the tree.
   *
   * @param toRemove
   *          the element to remove
   * @return the new tree with all instances of the given element removed from 
   *         it, or None if the given element is not in the tree
   */
  def removeAll(toRemove: A): Option[Tree[A]]

  /**
   * Finds any violations of invariants in this tree.
   *
   * @return either a description of the violated invariant or None
   */
  def checkForViolations: Option[String]

  /**
   * Throws an exception if there are any violations of invariants in this
   * tree.
   *
   * @param message
   *          a descriptive message to precede the error message in the
   *          exception
   */
  def throwIfViolationFound(message: String): Unit =
    checkForViolations.map((errorMessage: String) =>
      sys.error(message + ": " + errorMessage))

  /**
   * Adds the given element, checking for any violations of the tree's
   * invariants after the addition.  If a violation is found, an exception is
   * thrown.
   *
   * @param toAdd
   *          the element to add
   * @return the new tree with the given element added to it
   */
  def addAndCheck(toAdd: A): Tree[A] = {
    val result = add(toAdd)
    result.throwIfViolationFound("Found invariant violation after addition")
    result
  }
    
  /**
   * Deletes the given element from the tree, checking for any violations of
   * the tre's invariants after the removal.  If a violation is found, an
   * exception is thrown.
   *
   * @param toRemove
   *          the element to remove
   * @return the new tree with one instance of the given element removed from
   *         it, or None if the given element is not in the tree
   */
  def removeAndCheck(toRemove: A): Option[Tree[A]] =
    remove(toRemove).map((result: Tree[A]) => {
      result.throwIfViolationFound("Found invariant violation after removal")
      result
    })

}

/**
 * Nonempty list structure, used to indicate multiple instances of the same key
 * at a single node in a tree.
 */
private abstract sealed trait NonemptyList[A] { 
  def head: A 
}

private case class OneItem[A](h: A) extends NonemptyList[A] {
  override def head = h
  override def toString = h.toString
}

private case class MultipleItems[A](h: A, r: NonemptyList[A]) 
    extends NonemptyList[A] {
  override def head = h
  override def toString = h.toString + "," + r.toString
}

/**
 * Implementation of a binary search tree, in which every parent node's value
 * is greater than or equal to that of its left child (if any) and less than or
 * equal to that of its right child (if any).
 */

private trait BST[A] extends Tree[A] {
  // We specify that all operations on BSTs produce BSTs, not arbitrary trees
  override def add(toAdd: A): BST[A]
  override def remove(toRemove: A): Option[BST[A]] = removeImpl(toRemove, true)
  override def removeAll(toRemove: A): Option[BST[A]] = 
    removeImpl(toRemove, false)
  def removeImpl(toRemove: A, removeOnlyOne: Boolean): Option[BST[A]]

  /**
   * Returns all instances of the minimum value in the given tree.
   *
   * @return the smallest value in the given tree, or None if the given tree is
   *         empty
   */
  def minimum: Option[NonemptyList[A]]

}

/**
 * Mixin for implementing empty trees.
 */
private trait EmptyTree[A] extends BST[A] {
  override def removeImpl(toRemove: A, removeOnlyOne: Boolean) = None
  override def minimum: Option[NonemptyList[A]] = None
  override def toString = "Empty"

  // Empty trees are always valid.
  override def checkForViolations = None
}

private case class EmptyBST[A](ordering: Ordering[A]) extends BST[A] with EmptyTree[A] {
  override def add(toAdd: A): NonemptyBST[A] = 
    NonemptyBST[A](OneItem(toAdd), ordering, this, this)
}

private case class NonemptyBST[A](values: NonemptyList[A], ordering: Ordering[A], 
    left: BST[A], right: BST[A]) extends BST[A] {

  override def add(toAdd: A): NonemptyBST[A] = 
    if (ordering.equiv(toAdd, values.head))
      NonemptyBST[A](MultipleItems(toAdd, values), ordering, left, right)
    else if (ordering.lt(toAdd, values.head))
      NonemptyBST[A](values, ordering, left.add(toAdd), right)
    else
      NonemptyBST[A](values, ordering, left, right.add(toAdd))

  override def minimum: Option[NonemptyList[A]] =
    left match {
      case EmptyBST(_) => Some(values)
      case _ => left.minimum
    }

  override def removeImpl(toRemove: A, removeOnlyOne: Boolean): Option[BST[A]] = 
    if (ordering.equiv(toRemove, values.head))
      values match {
        case MultipleItems(_, tail) if removeOnlyOne =>
          Some(NonemptyBST[A](tail, ordering, left, right))
        case _ =>
          left match {
            case _: EmptyBST[_] => Some(right)
            case nonemptyLeft: NonemptyBST[_] =>
              right match {
                case _: EmptyBST[_] => Some(left)
                case _ =>
                  right.minimum.map((successor: NonemptyList[A]) =>
                    NonemptyBST[A](successor, ordering, left,
                                   // Calling Option.get here is safe because
                                   // we are removing the minimum value from
                                   // a tree, and the minimum value must
                                   // exist in the tree or it wouldn't be the
                                   // minimum value for that tree!
                                   right.removeAll(successor.head).get))
              }
          }
      }
    else if (ordering.lt(toRemove, values.head))
      left.removeImpl(toRemove, removeOnlyOne).map((newLeft: BST[A]) =>
        NonemptyBST[A](values, ordering, newLeft, right))
    else
      right.removeImpl(toRemove, removeOnlyOne).map((newRight: BST[A]) =>
        NonemptyBST[A](values, ordering, left, newRight))

  override def toString = 
    "BST(" + values + ", " + left.toString + ", " + right.toString + ")"

  /**
   * Verifies that every node's value is between that of its children.
   *
   * @return a case where a node's value doesn't fall between that of its 
   *         children, if any
   */
  override def checkForViolations: Option[String] =
    left.checkForViolations match {
      case leftViolation: Some[_] => leftViolation
      case _ => right.checkForViolations match {
        case rightViolation: Some[_] => rightViolation
        case _ => (left, right) match {
          case (NonemptyBST(leftVals, _, _, _), 
                NonemptyBST(rightVals, _, _, _)) =>
            checkLeft(leftVals.head) match {
              case leftTooBig: Some[_] => leftTooBig
              case _ => checkRight(rightVals.head)
            }
          case (_, NonemptyBST(rightVals, _, _, _)) =>
            checkRight(rightVals.head)
          case (NonemptyBST(leftVals, _, _, _), _) =>
            checkLeft(leftVals.head)
          case _ => None
        }
      }
    }

  private def checkLeft(a: A): Option[String] =
    if (ordering.lt(a, values.head))
      None
    else
      Some("Left child value " + a.toString + " is not less than " +
           values.head.toString)

  private def checkRight(a: A): Option[String] =
    if (ordering.gt(a, values.head))
      None
    else
      Some("Right child value " + a.toString + " is not greater than " +
           values.head.toString)

}

/**
 * Implementation of an AVL tree.
 */
private trait AVL[A] extends BST[A] {
  // We specify that all operations on AVL trees produce AVL trees.
  override def add(toAdd: A): AVL[A]
  override def remove(toRemove: A): Option[AVL[A]] = removeImpl(toRemove, true)
  override def removeAll(toRemove: A): Option[AVL[A]] = 
    removeImpl(toRemove, false)
  override def removeImpl(toRemove: A, removeOnlyOne: Boolean): Option[AVL[A]]

  /**
   * Indicates the height of this node, where leaves have a height of 1.
   */
  def height: Int

  /**
   * Indicates the difference between the height of the left and right children of
   * a node.
   */
  def balanceFactor: Int

  /**
   * Rotates the tree to the left at the root.
   */
  def rotateLeft: AVL[A]

  /**
   * Rotates the tree to the right at the root.
   */
  def rotateRight: AVL[A]

}

private case class EmptyAVL[A](ordering: Ordering[A]) extends AVL[A] with EmptyTree[A] {

  override def add(toAdd: A): NonemptyAVL[A] =
    NonemptyAVL[A](OneItem(toAdd), ordering, 1, this, this)

  override def height = 0
  override def balanceFactor = 0
  override def rotateLeft = sys.error("Should not need to rotate an empty tree")
  override def rotateRight = sys.error("Should not need to rotate an empty tree")

}

private case class NonemptyAVL[A](values: NonemptyList[A], ordering: Ordering[A],
  h: Int, left: AVL[A], right: AVL[A]) extends AVL[A] {

  private val difference = 1

  override def balanceFactor = left.height - right.height

  def rebalance: NonemptyAVL[A] = 
    if (balanceFactor < (-difference))
      if ((right.balanceFactor == -1) || (right.balanceFactor == 0))
        rotateLeft
      else if (right.balanceFactor == 1)
        NonemptyAVL[A](values, ordering, h, left, right.rotateRight).rotateLeft
      else
        sys.error("Right child has balance factor of " + right.balanceFactor +
                  ", value of -1, 0, or 1 was expected. This tree:" + 
                  System.getProperty("line.separator") + toString)
    else if (balanceFactor > difference)
      if ((left.balanceFactor == 1) || (left.balanceFactor == 0))
        rotateRight
      else if (left.balanceFactor == -1)
        NonemptyAVL[A](values, ordering, h, left.rotateLeft, right).rotateRight
      else
        sys.error("Left child has balance factor of " + right.balanceFactor +
                  ", value of -1, 0, or 1 was expected. This tree:" +
                  System.getProperty("line.separator") + toString)
    else
      this

  override def add(toAdd: A): NonemptyAVL[A] = 
    if (ordering.equiv(toAdd, values.head))
      NonemptyAVL[A](MultipleItems(toAdd, values), ordering, h, left, right)
    else if (ordering.lt(toAdd, values.head)) {
      val newLeft = left.add(toAdd)
      val newHeight = scala.math.max(newLeft.height, right.height) + 1
      NonemptyAVL[A](values, ordering, newHeight, newLeft, right).rebalance
    }
    else {
      val newRight = right.add(toAdd)
      val newHeight = scala.math.max(left.height, newRight.height) + 1
      NonemptyAVL[A](values, ordering, newHeight, left, newRight).rebalance
    }

  override def rotateLeft: NonemptyAVL[A] = {
    // We should only ever call this method if the right-hand side is nonempty.
    val nonemptyRight = right.asInstanceOf[NonemptyAVL[A]]
    val leftMax = scala.math.max(left.height, nonemptyRight.left.height)
    NonemptyAVL[A](nonemptyRight.values, ordering,
                   scala.math.max(leftMax + 1, nonemptyRight.right.height) + 1,
                   NonemptyAVL[A](values, ordering, leftMax + 1,
                                  left, nonemptyRight.left),
                   nonemptyRight.right)
  }

  override def rotateRight: NonemptyAVL[A] = {
    // We should only ever call this method if the left-hand side is nonempty.
    val nonemptyLeft = left.asInstanceOf[NonemptyAVL[A]]
    val rightMax = scala.math.max(right.height, nonemptyLeft.right.height)
    NonemptyAVL[A](nonemptyLeft.values, ordering,
                   scala.math.max(rightMax + 1, nonemptyLeft.left.height) + 1,
                   nonemptyLeft.left,
                   NonemptyAVL[A](values, ordering, rightMax + 1,
                                  nonemptyLeft.right, right))
  }

  override def minimum: Option[NonemptyList[A]] =
    left match {
      case EmptyAVL(_) => Some(values)
      case _ => left.minimum
    }

  override def removeImpl(toRemove: A, removeOnlyOne: Boolean): Option[AVL[A]] =
    if (ordering.equiv(toRemove, values.head))
      values match {
        case MultipleItems(_, tail) if removeOnlyOne =>
          Some(NonemptyAVL[A](tail, ordering, h, left, right))
        case _ =>
          left match {
            case _: EmptyAVL[_] => Some(right)
            case nonemptyLeft: NonemptyAVL[_] =>
              right match {
                case _: EmptyAVL[_] => Some(left)
                case _ =>
                  right.minimum.map((successor: NonemptyList[A]) => {
                    // Calling Option.get here is safe because we are removing
                    // the minimum value from a tree, and the minimum value must
                    // exist in the tree or it wouldn't be the minimum value for
                    // that tree!
                    val newRight = right.removeAll(successor.head).get
                    val newHeight = scala.math.max(left.height, 
                                                   newRight.height) + 1
                    NonemptyAVL[A](successor, ordering, newHeight, left, newRight).rebalance
                  })
              }
          }
      }
    else if (ordering.lt(toRemove, values.head))
      left.removeImpl(toRemove, removeOnlyOne).map((newLeft: AVL[A]) => {
        val newHeight = scala.math.max(newLeft.height, right.height) + 1
        NonemptyAVL[A](values, ordering, newHeight, newLeft, right).rebalance
      })
    else
      right.removeImpl(toRemove, removeOnlyOne).map((newRight: AVL[A]) => {
        val newHeight = scala.math.max(left.height, newRight.height) + 1
        NonemptyAVL[A](values, ordering, newHeight, left, newRight).rebalance
      })

  override def toString =
    "AVL(" + values + ", " + left.toString + ", " + right.toString + ")"

  override def height = h

  /**
   * Verifies that every node's value is between that of its children and that
   * the height of the children does not differ by more than 1.
   *
   * @return a case where a node's value doesn't fall between that of its 
   *         children or where the heights of the children differ too much, if
   *         any
   */
  override def checkForViolations: Option[String] =
    if (scala.math.abs(left.height - right.height) > difference)
      Some("Height of left child (" + left.height + 
           ") differs from height of right child (" + right.height + 
           ") by more than " + difference)
    else
      left.checkForViolations match {
        case leftViolation: Some[_] => leftViolation
        case _ => right.checkForViolations match {
          case rightViolation: Some[_] => rightViolation
          case _ => (left, right) match {
            case (NonemptyAVL(leftVals, _, _, _, _), 
                  NonemptyAVL(rightVals, _, _, _, _)) =>
              checkLeft(leftVals.head) match {
                case leftTooBig: Some[_] => leftTooBig
                case _ => checkRight(rightVals.head)
              }
            case (_, NonemptyAVL(rightVals, _, _, _, _)) =>
              checkRight(rightVals.head)
            case (NonemptyAVL(leftVals, _, _, _, _), _) =>
              checkLeft(leftVals.head)
            case _ => None
          }
        }
      }

  private def checkLeft(a: A): Option[String] =
    if (ordering.lt(a, values.head))
      None
    else
      Some("Left child value " + a.toString + " is not less than " +
           values.head.toString)

  private def checkRight(a: A): Option[String] =
    if (ordering.gt(a, values.head))
      None
    else
      Some("Right child value " + a.toString + " is not greater than " +
           values.head.toString)

}

/**
 * Implementation o a red-black tree.
 */
private trait RBT[A] extends BST[A] {
  // We specify that all operations on red-black trees produce red-black trees.
  override def add(toAdd: A): RBT[A]
  override def remove(toRemove: A): Option[RBT[A]] = removeImpl(toRemove, true)
  override def removeAll(toRemove: A): Option[RBT[A]] = 
    removeImpl(toRemove, false)
  override def removeImpl(toRemove: A, removeOnlyOne: Boolean): Option[RBT[A]]

  def isBlack: Boolean;
  def turnRed: RBT[A];
  def turnBlack: RBT[A];

} 

private case class EmptyRBT[A](ordering: Ordering[A]) extends RBT[A] with EmptyTree[A] {
  override def add(toAdd: A): NonemptyRBT[A] = 
    NonemptyRBT[A](OneItem(toAdd), ordering, true, this, this)

  override def isBlack: Boolean = true;
  override def turnRed: EmptyRBT[A] = sys.error("Cannot turn an empty (leaf) node red")
  override def turnBlack: EmptyRBT[A] = this;
}

private case class NonemptyRBT[A](values: NonemptyList[A], ordering: Ordering[A],
  black: Boolean, left: RBT[A], right: RBT[A]) extends RBT[A] {

  override def turnRed: NonemptyRBT[A] = 
    NonemptyRBT[A](values, ordering, false, left, right)

  override def turnBlack: NonemptyRBT[A] = 
    NonemptyRBT[A](values, ordering, true, left, right)

  override def add(toAdd: A): NonemptyRBT[A] = 
    if (ordering.equiv(toAdd, values.head))
      NonemptyRBT[A](MultipleItems(toAdd, values), ordering, black, left, right)
    else if (ordering.lt(toAdd, values.head)) {
      val newLeft = left.add(toAdd)
      left match {
        case EmptyRBT(_) =>
          if (black)
            NonemptyRBT(values, ordering, black, newLeft.turnRed, right)
          else
            this // TODO Handle this case
        case _ => this // TODO Handle this case
      }
    }
    else {
      // TODO Mirror the behavior from the left
      this
    }

  private def rotateLeft: NonemptyRBT[A] = this // TODO Implement

  private def rotateRight: NonemptyRBT[A] = this // TODO Implement

  override def minimum: Option[NonemptyList[A]] =
    left match {
      case EmptyRBT(_) => Some(values)
      case _ => left.minimum
    }

  override def removeImpl(toRemove: A, removeOnlyOne: Boolean): Option[RBT[A]] =
    None // TODO Implement

  override def isBlack = black

  override def toString =
    "RBT(" + values + ", " + left.toString + ", " + right.toString + ")"

  /**
   * Verifies that all of the properties of red-black trees hold for this
   * tree.  The properties of red-black trees are:
   * <ol>
   *   <li>TODO</li>
   * </ol>
   *
   * @return a case where a node's value doesn't fall between that of its 
   *         children or where the heights of the children differ too much, if
   *         any
   */
  override def checkForViolations: Option[String] = sys.error("todo") // TODO

}

/**
 * Code for testing tree-based data structures.
 */
object TreeTest {

  /**
   * Given a tree and two lists of values, inserts all of the elements in
   * the first list into the tree and then removes all of the elements in
   * the second list from the tree.
   *
   * @param tree
   *          the initial tree to which all the operations should be applied
   * @param toAdd
   *          the values to add
   * @param toRemove
   *          the values to remove
   * @return a string describing the tree that was tested
   */
  def testTree[A](tree: Tree[A], toAdd: Seq[A], toRemove: Seq[A]): String = {
    val withAdditions: Tree[A] = toAdd.foldLeft(tree)(_ addAndCheck _)
    toRemove.foldLeft(withAdditions)((t: Tree[A], a: A) => 
      t.removeAndCheck(a).getOrElse(
        sys.error("Was unable to find " + a.toString + " in " + t.toString)))
    if ((toAdd.size > 20) || (toRemove.size > 20))
      "Added " + toAdd.size.toString + " and removed " + 
        toRemove.size.toString + " elements"
    else
      withAdditions.toString
  }

  /**
   * Generates a random list of integers or takes a list of integers as 
   * arguments, inserts them into a tree, and then deletes each of them
   * from the tree.  Also runs tests where the integers are sorted and/or
   * reversed before insertion into or removal from the tree.
   *
   * @param args
   *          either an empty array, indicating that a list of 10 random
   *          integers should be put into a tree, or a single integer, indicating
   *          that a random list of the given size should be put into a tree, or
   *          a list of two or more integers that should be put into a tree
   */
  def main(args: Array[String]) = {
    val values =
      if (args.length == 0)
        Common.randomList(10)
      else if (args.length == 1)
        Common.randomList(java.lang.Integer.parseInt(args(0)))
      else
        Common.parseInts(args)
    val sortedValues = Sort.mergeSort(values)
    val reversedValues = sortedValues.reverse
    val allValueLists = List((values, "random values"),
                             (sortedValues, "sorted values"),
                             (reversedValues, "reverse-sorted values"))

    List((Tree.binarySearchTree[Int], "Binary search tree"), 
         (Tree.avlTree[Int], "AVL tree")/* , TODO Uncomment RBT
         (Tree.redBlackTree[Int], "Red-black tree") */).flatMap(
      _ match {
        case (tree, label1) => allValueLists.flatMap(
          _ match {
            case (addVals, label2) => allValueLists.map(
              _ match {
                case(removeVals, label3) =>
                  Runner.printAndTime(() => 
                    testTree(tree, addVals, removeVals).toString, 
                    label1 + ": " + label2 + " added, " + label3 + " removed")
              })
          })
      })
  }

}
