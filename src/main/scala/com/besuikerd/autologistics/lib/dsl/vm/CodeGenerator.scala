package com.besuikerd.autologistics.lib.dsl.vm

import com.besuikerd.autologistics.lib.dsl._

object CodeGenerator {
  def generate(list:List[Statement]):List[Instruction] = OpenScope +: list.map(generate).flatten :+ CloseScope

  def generate(ast:ASTNode):List[Instruction] = ast match {

    case ExpressionStatement(e) => generate(e) :+ Pop
    case Assignment(binding, l:LambdaExpression) => lambdaInstructions(Some(binding), l) :+ Put(binding)
    case Assignment(binding, e) => generate(e) :+ Put(binding)

    case VariableExpression(v) => List(Get(v))

    case l:LambdaExpression => lambdaInstructions(None, l)

    case BlockExpression(body) => {
      val statements = body.flatMap(generate)
      val(xs, x) = (statements.init, statements.last)
      println("last in block: " + x)
      val fixedStatements = if(x equals Pop) xs else xs :+ Push(NilValue)
      OpenScope +: fixedStatements :+ CloseScope
    }

    case IfElseExpression(condition, ifExp, elseExp) => generate(condition) :+ Branch(generate(ifExp), elseExp.map(generate).getOrElse(List(Push(NilValue))))

    case Application(e, args) => generate(e) ++ args.flatMap(generate) :+ Call(args.size)

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

    case ObjectExpression(mapping) => mapping.values.flatMap(generate).toList :+ PushObject(mapping.keys.toList.reverse)
    case ObjectFieldExpression(exp, bindings) => generate(exp) :+ Select(bindings)

    case NaturalNumberConstant(n) => List(Push(NaturalNumber(n)))
    case RealNumberConstant(n) => List(Push(RealNumber(n)))
    case StringLiteral(s) => List(Push(StringValue(s)))
    case BooleanConstant(b) => List(Push(BooleanValue(b)))
  }

  private def lambdaInstructions(name:Option[String], lambda:LambdaExpression): List[Instruction] ={
    val bodyInstructions = generate(lambda.body)
    List(PushClosure(name, lambda.bindings, findVariables(bodyInstructions).filter(!lambda.bindings.contains(_)), bodyInstructions))
  }

  private def findVariables(instructions: List[Instruction]):List[String] = instructions.collect{case Get(v) => v}
}
