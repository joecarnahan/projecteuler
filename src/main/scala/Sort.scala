/**
 * Sorting and sorting-related functions.
 *
 * @author Joe Carnahan (joseph.carnahan@gmail.com)
 */
object Sort {

  /**
   * Inserts the given element into the given list in sorted order.
   *
   * @param element
   *          the element to insert
   * @param list
   *          the sorted list into which the element should be inserted
   * @return the result of inserting the given element into the given list
   */
  def insert[A](element: A, list: List[A])(implicit ordering: Ordering[A]): List[A] =
    list.foldLeft((false, List[A]()))((state: (Boolean, List[A]), next: A) => {
      val (inserted, processed) = state
      if (inserted)
        (true, next :: processed)
      else if (ordering.gt(element, next))
        (false, next :: processed)
      else
        (true, next :: (element :: processed))
    }) match {
      case (true, result) => result.reverse
      case (false, result) => (element :: result).reverse
    }

  /**
   * Sorts a sequence using insertion sort.
   * 
   * @param toSort
   *          the sequence to sort
   * @return a sequence containing the elements of the given sequence in sorted
   *         order
   */
  def insertionSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): List[A] = {

    def rec(processed: List[A], remaining: Seq[A]): List[A] = 
      if (remaining.isEmpty)
        processed
      else
        rec(insert(remaining.head, processed), remaining.tail)

    if (seq.isEmpty)
      List[A]()
    else
     rec(List(seq.head), seq.tail)

  }

  /**
   * Threshold value, below which other sorting algorithms default to using
   * insertion sort.
   */
  val sortSizeThreshold = 10

  /**
   * Sorts a sequence using quicksort.
   *
   * @param toSort
   *          the sequence to sort
   * @return a sequence containing the elements of the given sequence in sorted
   *         order
   */
  def quickSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): Seq[A] =
    if (seq.size < sortSizeThreshold)
      insertionSort(seq)(ordering)
    else {
      quickSort(seq.filter(ordering.lt(_,seq.head))) ++
      seq.filter(ordering.equiv(_,seq.head)) ++
      quickSort(seq.filter(ordering.gt(_,seq.head)))
    }
    
  /**
   * Sorts a sequence using heapsort.
   *
   * @param toSort
   *          the sequence to sort
   * @return a sequence containing the elements of the given sequence in sorted
   *         order
   */
  def heapSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): List[A] = 
    (Heap[A](ordering.reverse) ++ seq).toList

  /**
   * Merges two sorted sequences.
   *
   * @param seq1 
   *          the first sorted sequence to examine
   * @param seq2
   *          the second sorted sequence to examine
   * @return a list that contains all of the elements from both sequences
   *         arranged in sorted order
   */
  def merge[A](seq1: Seq[A], seq2: Seq[A])(implicit ordering: Ordering[A]): List[A] = {

    def addAll(s: Seq[A], acc: List[A]): List[A] =
      if (s.isEmpty)
        acc
      else
        addAll(s.tail, s.head :: acc)

    def mergeRec(s1: Seq[A], s2: Seq[A], acc: List[A]): List[A] =
      if (s1.isEmpty)
        addAll(s2, acc).reverse
      else if (s2.isEmpty)
        addAll(s1, acc).reverse
      else if (ordering.lt(s1.head, s2.head))
        mergeRec(s1.tail, s2, s1.head :: acc)
      else
        mergeRec(s1, s2.tail, s2.head :: acc)

    mergeRec(seq1, seq2, List[A]())

  }

  /**
   * Sorts a sequence using merge sort.
   *
   * @param toSort
   *          the sequence to sort
   * @return a sequence containing the elements of the given sequence in sorted
   *         order
   */
  def mergeSort[A](seq: Seq[A])(implicit ordering: Ordering[A]): List[A] =
    if (seq.size < sortSizeThreshold)
      insertionSort(seq)(ordering)
    else {
      val (list1, list2) = seq.splitAt(seq.size / 2)
      merge(mergeSort(list1), mergeSort(list2))
    }

}

/**
 * Code for testing sorting functions.
 */
object SortTest {

  import Sort._
  
  /**
   * Generates a random list of integers or takes a list of integers as 
   * arguments and sorts them using each of the available sorting functions.
   *
   * @param args
   *          either an empty array, indicating that a list of 10 random
   *          integers should be sorted, or a single integer, indicating
   *          that a random list of the given size should be sorted, or a
   *          list of two or more integers that should be sorted
   */
  def main(args: Array[String]): Unit = {
    val toSort =
      if (args.length == 0)
        Common.randomList(10)
      else if (args.length == 1)
        Common.randomList(java.lang.Integer.parseInt(args(0)))
      else
        Common.parseInts(args)
    List((insertionSort[Int] _, "Insertion sort"),
         (quickSort[Int] _,     "Quicksort"),
         (heapSort[Int] _,      "Heapsort"),
         (mergeSort[Int] _,     "Merge sort")).map(
      _ match {
        case (sort, label) => 
          Runner.printAndTime(() => sort(toSort).toString, label)
      })
  }

}
