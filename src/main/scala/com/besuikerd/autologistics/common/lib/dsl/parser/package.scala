package com.besuikerd.autologistics.common.lib.dsl

import akka.actor.Status.Success

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.util.parsing.combinator.Parsers

package object parser {
  trait ParserImplicits extends Parsers{
    def concat(tilde: ~[_,_]):String = tilde match{
      case a ~ b ~ c => a.toString + concat(new ~(b,c))
      case a ~ b => a.toString + b.toString
    }

    def repsepSplit[A,B](p:Parser[A], q:Parser[B], sep:Parser[_]):Parser[(List[A], List[B])] = repsep(p ^^ Left.apply | q ^^ Right.apply, sep).map(elems => {
      elems.foldRight((List[A](), List[B]())){
        case (Left(a), (as, bs)) => (a::as, bs)
        case (Right(b), (as, bs)) => (as, b::bs)
        case (a, (as,bs)) => (as,bs)
      }
    })

    /**
     * just like repsep, but expect at least n repetitions
     */
    def repNsep[A](n:Int, p:Parser[A], q:Parser[Any]): Parser[List[A]] = Parser{ in =>
      repsep(p, q).apply(in) match{
        case s@Success(result, next) => {
          val size = result.size
          if(size >= n) s else Failure(s"expected at least $n repetitions, got $size", next)
        }
        case nope:NoSuccess => nope
      }
    }
  }
}
