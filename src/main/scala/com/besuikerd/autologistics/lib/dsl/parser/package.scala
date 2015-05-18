package com.besuikerd.autologistics.lib.dsl

import scala.util.parsing.combinator.Parsers

package object parser {
  trait ParserImplicits extends Parsers{
    def concat(tilde: ~[_,_]):String = tilde match{
      case a ~ b ~ c => a.toString + concat(new ~(b,c))
      case a ~ b => a.toString + b.toString
    }
  }
}
