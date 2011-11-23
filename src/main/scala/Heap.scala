/**
 * Scala implementation of a max-heap, also known as a priority queue.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */

object Heap {

  /**
   * Creates a new empty heap.
   *
   * @param ordering
   *          used to specify how elements in the heap should be compared to 
   *          each other
   */
  def apply[A](implicit ordering: Ordering[A]): Heap[A] = 
    new EmptyHeap[A](ordering)
}

/**
 * The heap interface.
 */
trait Heap[A] extends Iterable[A] {

  /**
   * Gets the largest value from the heap and returns it along with the new state
   * of the heap.
   *
   * @return a pair containing the largest value and the new state of the heap,
   *         where None is the largest value if the heap is empty
   */
  def get: ((Option[A], Heap[A]))

  /**
   * Adds the given value to the heap.
   *
   * @param toAdd
   *          the value to add
   * @return the new state of the heap
   */
  def put(toAdd: A): Heap[A]

  /**
   * Indicates if this heap is empty or not.
   *
   * @return <code>true</code> if and only if this heap is empty
   */
  def isEmpty: Boolean

  /**
   * Provides an iterator that calls <code>get</code> on this heap to get all of
   * its elements until it is empty.
   *
   * @return an iterator for this heap
   */
  def iterator: Iterator[A] = new HeapIterator[A](this)

  /**
   * Adds all of the elements of the given sequence to this heap using
   * <code>put()</code>.
   *
   * @param toAdd
   *          the values to add
   */
  def ++(toAdd: Seq[A]): Heap[A] =
    if (toAdd.isEmpty)
      this
    else
      put(toAdd.head) ++ toAdd.tail

}

private class HeapIterator[A](initialHeap: Heap[A]) extends Iterator[A] {
  var heap = initialHeap
  override def hasNext: Boolean = !(heap.isEmpty)
  override def next: A = {
    val (result, newHeap) = heap.get
    heap = newHeap
    // We expect an error if you call next() on an empty heap's iterator.
    // Don't look at me, I didn't invent the interface. ;-)
    result.get
  }
}

/**
 * Implementation of an empty heap.
 */
private case class EmptyHeap[A](ordering: Ordering[A]) extends Heap[A] {
  override def get: ((Option[A], Heap[A])) = (None, this)
  override def put(toAdd: A): NonemptyHeap[A] = new LeafHeap(toAdd, ordering)
  override def isEmpty = true
}

/**
 * Implementation of a nonempty heap.
 */
private abstract case class NonemptyHeap[A](value: A, ordering: Ordering[A]) 
    extends Heap[A] {
  override def isEmpty = false
}

/**
 * Implementation of a heap with a single value.
 */
private class LeafHeap[A](value: A, ordering: Ordering[A]) 
    extends NonemptyHeap(value, ordering) {
  override def get: ((Option[A], Heap[A])) = (Some(value), new EmptyHeap(ordering))
  override def put(toAdd: A): NonemptyHeap[A] =
    if (ordering.gt(toAdd, value))
      new OneChildHeap(toAdd, ordering, this)
    else
      new OneChildHeap(value, ordering, new LeafHeap(toAdd, ordering))
}

/**
 * Implementation of a heap with a root node that has only one child.
 */
private class OneChildHeap[A](value: A, ordering: Ordering[A], child: NonemptyHeap[A]) 
    extends NonemptyHeap(value, ordering) {
  override def get: ((Option[A], Heap[A])) = (Some(value), child)
  override def put(toAdd: A): NonemptyHeap[A] =
    if (ordering.gt(toAdd, value))
      new TwoChildHeap(toAdd, ordering, child, new LeafHeap(value, ordering))
    else
      new TwoChildHeap(value, ordering, child, new LeafHeap(toAdd, ordering))
}

/**
 * Implementation of a heap whose root has two children.
 */
private class TwoChildHeap[A](value: A, ordering: Ordering[A], 
                              left: NonemptyHeap[A], right: NonemptyHeap[A])
    extends NonemptyHeap(value, ordering) {

  override def get: ((Option[A], Heap[A])) = (Some(value), {
    val (lesserChild, greaterChild) = 
      if (ordering.lt(left.value, right.value)) (left, right) else (right, left)
    greaterChild.get._2 match {
      case empty: EmptyHeap[_] => 
        new OneChildHeap(greaterChild.value, ordering, lesserChild)
      case nonempty: NonemptyHeap[_] => 
        new TwoChildHeap(greaterChild.value, ordering, nonempty, lesserChild)
    }
  })

  override def put(toAdd: A): NonemptyHeap[A] = {
    val (lesserChild, greaterChild) = 
      if (ordering.lt(left.value, right.value)) (left, right) else (right, left)
    val (valueToPush, newRoot) =
      if (ordering.lt(value, toAdd)) (value, toAdd) else (toAdd, value)
    new TwoChildHeap(newRoot, ordering, 
                     // We don't expose the NonemptyHeap class to clients of
                     // this interface.  However, we know that put always
                     // actually returns a nonempty heap.
                     lesserChild.put(valueToPush).asInstanceOf[NonemptyHeap[A]], 
                     greaterChild)
  }

}
