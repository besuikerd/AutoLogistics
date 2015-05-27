package com.besuikerd.autologistics.lib.dsl

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
      }
    })
  }
}
