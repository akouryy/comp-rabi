package rabi
package ut

import scala.collection.{mutable, AbstractIterator}

final class DoublyLinkedList[A](initialElements: IterableOnce[A] = Nil):

  private var _head: Option[Entry] = None
  def headOption: Option[Entry] = _head

  private var _last: Option[Entry] = None
  def lastOption: Option[Entry] = _last

  def isEmpty: Boolean = _head.isEmpty
  def nonEmpty: Boolean = _head.nonEmpty

  def +=(a: A): Unit =
    val entry = Some(Entry(a, lastOption, None))
    if isEmpty
      _head = entry
      _last = entry
    else
      lastOption.get.next = entry
      _last = entry

  def ++=(as: IterableOnce[A]): Unit = as.iterator.foreach(+=)

  def iterator = this.Iterator()

  def foreach(fn: Entry => Unit): Unit = iterator.foreach(fn)

  def map[B](fn: Entry => B): DoublyLinkedList[B] = DoublyLinkedList(iterator.map(fn))

  def mapToSeq[B](fn: Entry => B): Seq[B] = iterator.map(fn).toSeq

  // init
  ++=(initialElements)

  final class Entry private[DoublyLinkedList](
    val elem: A,
    private[DoublyLinkedList] var prev: Option[Entry],
    private[DoublyLinkedList] var next: Option[Entry],
  ):
    private def setPrevNext(e: Option[Entry]) =
      prev match
        case Some(p) => p.next = e
        case None => _head = e

    private def setNextPrev(e: Option[Entry]) =
      next match
        case Some(n) => n.prev = e
        case None => _last = e

    def insertBefore(a: A): Unit =
      val entry = Some(Entry(a, prev, Some(this)))
      val e = this
      setPrevNext(entry)
      prev = entry

    def insertAfter(a: A): Unit =
      val entry = Some(Entry(a, Some(this), next))
      setNextPrev(entry)
      next = entry

    /**
     * removes `this` from the list.

     * If an iterator `it` points to `this`,
     * it remains valid after the removal, and `it.next()` will return `this.next`.
     */
    def remove(): Unit =
      setPrevNext(next)
      setNextPrev(prev)

    override def toString: String = s"Entry($elem, ${prev.productPrefix}, ${next.productPrefix})"
  end Entry

  final class Iterator extends AbstractIterator[Entry]:
    private[this] var current = Option.empty[Entry]

    override def hasNext = current ne lastOption

    override def next(): Entry =
      if hasNext
        current = current.fold(headOption)(_.next)
        current.get
      else
        Iterator.empty.next()

  end Iterator

end DoublyLinkedList
