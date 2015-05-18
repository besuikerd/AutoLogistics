package com.besuikerd.autologistics.lib.dsl.vm

import com.besuikerd.autologistics.lib.dsl._

object CodeGenerator {
  def generate(list:List[Statement]):List[Instruction] = OpenScope +: list.map(generate).flatten :+ CloseScope

  def generate(ast:ASTNode):List[Instruction] = ast match {

    case ExpressionStatement(e) => generate(e) :+ Pop
    case Assignment(binding, e) => generate(e) :+ Put(binding)

    case VariableExpression(v) => List(Get(v))

    case Application(e, args) => generate(e) ++ args.map(generate).flatten :+ Call(args.size)

    case Add(e1, _, e2) => generate(e1) ++ generate(e2) :+ AddInstruction
    case Sub(e1, _, e2) => generate(e1) ++ generate(e2) :+ SubInstruction
    case Mul(e1, _, e2) => generate(e1) ++ generate(e2) :+ MulInstruction
    case Div(e1, _, e2) => generate(e1) ++ generate(e2) :+ DivInstruction
    case Mod(e1, _, e2) => generate(e1) ++ generate(e2) :+ ModInstruction

    case GT(e1, _, e2) => generate(e1) ++ generate(e2) :+ GTInstruction
    case GTE(e1, _, e2) => generate(e1) ++ generate(e2) :+ GTEInstruction
    case LT(e1, _, e2) => generate(e1) ++ generate(e2) :+ LTInstruction
    case LTE(e1, _, e2) => generate(e1) ++ generate(e2) :+ LTEInstruction
    case EQ(e1, _, e2) => generate(e1) ++ generate(e2) :+ EQInstruction
    case NEQ(e1, _, e2) => generate(e1) ++ generate(e2) :+ NEQInstruction

    case NaturalNumberConstant(n) => List(Push(NaturalNumber(n)))
    case RealNumberConstant(n) => List(Push(RealNumber(n)))
    case StringLiteral(s) => List(Push(StringValue(s)))
    case BooleanConstant(b) => List(Push(BooleanValue(b)))
  }
}
