package com.besuikerd.autologistics.lib.collection

import scala.collection.generic._
import scala.collection.mutable
import scala.collection.mutable.{Builder, ListBuffer}

trait Stack[A]
extends mutable.AbstractSeq[A]
  with mutable.Seq[A]
  with mutable.SeqLike[A, Stack[A]]
  with GenericTraversableTemplate[A, Stack]
  with mutable.Cloneable[Stack[A]]
  with Serializable
{
  protected var elems:List[A]

  override def companion = Stack

  def push(elem: A): Unit = elems = elem :: elems
  def top() = elems.head
  def pop(): A = {
    val elem = elems.head
    elems = elems.tail
    elem
  }

  def clear() = elems = Nil

  def prepend(elems: List[A]) = this.elems = elems ++ this.elems
  def ::=  = prepend _

  override def update(index: Int, elem: A): Unit = {
    val (left, right) = elems.splitAt(index)
    elems = left ++ (elem :: right.tail)
  }

  override def length: Int = elems.length

  override def apply(index: Int): A = elems.apply(index)

  override def iterator: Iterator[A] = elems.iterator
}

object Stack extends SeqFactory[Stack]{

  def apply[A](list:List[A]): Stack[A] = new Stack[A]{override var elems = list}

  class StackBuilder[A] extends Builder[A, Stack[A]] {
    val lbuff = new ListBuffer[A]
    def +=(elem: A) = { lbuff += elem; this }
    def clear() = lbuff.clear()
    def result = Stack(lbuff.result)
  }

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Stack[A]] = ReusableCBF.asInstanceOf[GenericCanBuildFrom[A]]
  def newBuilder[A]: Builder[A, Stack[A]] = new StackBuilder[A]
  val empty: Stack[Nothing] = Stack(Nil)
}
