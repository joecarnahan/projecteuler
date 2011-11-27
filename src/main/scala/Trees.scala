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
  def redBlackTree[A](implicit ordering: Ordering[A]): Tree[A] = new DummyTree[A]

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

private case class EmptyBST[A](ordering: Ordering[A]) extends BST[A] {

  override def add(toAdd: A): BST[A] = 
    NonemptyBST[A](OneItem(toAdd), ordering, this, this)

  override def removeImpl(toRemove: A, removeOnlyOne: Boolean): Option[BST[A]] =
    None
    
  override def minimum: Option[NonemptyList[A]] = None

  override def toString = "Empty"

}

private case class NonemptyBST[A](values: NonemptyList[A], ordering: Ordering[A], 
    left: BST[A], right: BST[A]) extends BST[A] {

  override def add(toAdd: A): BST[A] = 
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
}

private case class EmptyAVL[A](ordering: Ordering[A]) extends AVL[A] {

  override def add(toAdd: A): AVL[A] =
    NonemptyAVL[A](OneItem(toAdd), ordering, 1, this, this)

  override def removeImpl(toRemove: A, removeOnlyOne: Boolean): Option[AVL[A]] =
    None
    
  override def minimum: Option[NonemptyList[A]] = None

  override def toString = "Empty"

  override def height = 0

}

private case class NonemptyAVL[A](values: NonemptyList[A], ordering: Ordering[A],
  h: Int, left: AVL[A], right: AVL[A]) extends AVL[A] {

  override def add(toAdd: A): AVL[A] = 
    if (ordering.equiv(toAdd, values.head))
      NonemptyAVL[A](MultipleItems(toAdd, values), ordering, h, left, right)
    else if (ordering.lt(toAdd, values.head)) {
      val newLeft = left.add(toAdd)
      val newHeight = scala.math.max(newLeft.height, right.height) + 1
      if ((newLeft.height - right.height) < 2)
        NonemptyAVL[A](values, ordering, newHeight, newLeft, right)
      else
        NonemptyAVL[A](values, ordering, newHeight, newLeft, right).rotateRight
    }
    else {
      val newRight = right.add(toAdd)
      val newHeight = scala.math.max(left.height, newRight.height) + 1
      if ((newRight.height - left.height) < 2)
        NonemptyAVL[A](values, ordering, newHeight, left, newRight)
      else
        NonemptyAVL[A](values, ordering, newHeight, left, newRight).rotateLeft 
    }

  private def rotateLeft: NonemptyAVL[A] = {
    // We should only ever call this method if the right-hand side is nonempty.
    val nonemptyRight = right.asInstanceOf[NonemptyAVL[A]]
    val leftMax = scala.math.max(left.height, nonemptyRight.left.height)
    NonemptyAVL[A](nonemptyRight.values, ordering,
                   scala.math.max(leftMax + 1, nonemptyRight.right.height) + 1,
                   NonemptyAVL[A](values, ordering, leftMax + 1,
                                  left, nonemptyRight.left),
                   nonemptyRight.right)
  }

  private def rotateRight: NonemptyAVL[A] = {
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
                    if ((left.height - newRight.height) < 2)
                      NonemptyAVL[A](successor, ordering, newHeight, left, newRight)
                    else
                      NonemptyAVL[A](successor, ordering, newHeight, left, newRight).rotateRight
                  })
              }
          }
      }
    else if (ordering.lt(toRemove, values.head))
      left.removeImpl(toRemove, removeOnlyOne).map((newLeft: AVL[A]) => {
        val newHeight = scala.math.max(newLeft.height, right.height) + 1
        if ((right.height - newLeft.height) < 2)
          NonemptyAVL[A](values, ordering, newHeight, newLeft, right)
        else
          NonemptyAVL[A](values, ordering, newHeight, newLeft, right).rotateLeft
      })
    else
      right.removeImpl(toRemove, removeOnlyOne).map((newRight: AVL[A]) => {
        val newHeight = scala.math.max(left.height, newRight.height) + 1
        if ((left.height - newRight.height) < 2)
          NonemptyAVL[A](values, ordering, newHeight, left, newRight)
        else
          NonemptyAVL[A](values, ordering, newHeight, left, newRight).rotateRight
      })

  override def toString =
    "AVL(" + values + ", " + left.toString + ", " + right.toString + ")"

  override def height = h

}

// TODO Remove when not needed
class DummyTree[A] extends Tree[A] {
  override def add(toAdd: A): Tree[A] = this
  override def remove(toRemove: A): Option[Tree[A]] = None
  override def removeAll(toRemove: A): Option[Tree[A]] = None
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
    val withAdditions: Tree[A] = toAdd.foldLeft(tree)(_ add _)
    toRemove.foldLeft(withAdditions)((t: Tree[A], a: A) => t.remove(a) match {
      case Some(result) =>
        result
      case None => sys.error("Was unable to find " + a.toString + " in " + t.toString)
    })
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
         (Tree.avlTree[Int], "AVL tree") /*,
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
