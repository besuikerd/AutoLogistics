package com.besuikerd.autologistics.common.lib.dsl.old.vm

import com.besuikerd.autologistics.common.lib.dsl._
import com.besuikerd.autologistics.common.lib.dsl.old._

import scala.collection.mutable.ListBuffer

object OldCodeGenerator {
  def generate(list:List[Statement]):List[Instruction] = OpenScope +: list.map(generate).flatten :+ CloseScope

  def generate(ast:ASTNode):List[Instruction] = ast match {

    case ExpressionStatement(e) => generate(e) :+ Pop
    case Assignment(binding, l:LambdaExpression, isLocal) => lambdaInstructions(Some(binding), l) :+ Put(binding)
    case Assignment(binding, e, isLocal) => generate(e) :+ Put(binding)
    case AssignField(objExp, fields, binding) => {
      val (init, last) = fields.splitAt(fields.length - 1)
      val getFields = init.flatMap{s => List(Push(StringValue(s)), GetField)}
      generate(objExp) ++ getFields ++ (Push(StringValue(last.head)) +: generate(binding) :+ PutField)
    }
    case AssignIndex(obj, indexes, binding) => {
      val (init, last) = indexes.splitAt(indexes.length - 1)
      val getIndexes = init.flatMap(e => generate(e) :+ GetField)
      generate(obj) ++ getIndexes ++ generate(last.head) ++ generate(binding) :+ PutField
    }

    case WhileStatement(condition, body) => {
      var bodyStatements = generate(body)
      val conditionStatements = generate(condition)
      if(bodyStatements.head.equals(OpenScope)){ //remove scope for while loop to mutate variables out of this scope
        bodyStatements = bodyStatements.tail
        val init = bodyStatements.init
        if(init.last.equals(CloseScope)){
          bodyStatements = init.init :+ bodyStatements.last
        }
      }
      conditionStatements :+ RepeatedBranch(bodyStatements :+ Load(conditionStatements), Nil)
    }
    case VariableExpression(v) => List(Get(v))

    case l:LambdaExpression => lambdaInstructions(None, l)

    case BlockExpression(body) => {
      val statements = body.flatMap(generate)
      if(statements.nonEmpty){
        val(xs, x) = (statements.init, statements.last)
        val fixedStatements = if(x equals Pop) xs else statements :+ Push(NilValue)
        OpenScope +: fixedStatements :+ CloseScope
      } else List(Push(NilValue))
    }

    case Instructions(instructions) => ??? //instructions

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

    case And(e1, _, e2) => generate(e1) ++ generate(e2) :+ AndInstruction
    case Or(e1, _, e2) => generate(e1) ++ generate(e2) :+ OrInstruction

    case ObjectExpression(mapping) => mapping.values.flatMap(generate).toList :+ PushObject(mapping.keys.toList.reverse)
    case ObjectFieldExpression(exp, bindings) => generate(IndexExpression(exp, bindings.map(StringLiteral)))
    case ListExpression(values) => values.reverse.flatMap(generate) :+ PushList(values.length)
    case IndexExpression(exp, indexes) => {
      val getIndexes = indexes.flatMap{e => generate(e) :+ GetField}
      generate(exp) ++ getIndexes
    }

    case NaturalNumberConstant(n) => List(Push(NaturalNumber(n)))
    case RealNumberConstant(n) => List(Push(RealNumber(n)))
    case StringLiteral(s) => List(Push(StringValue(s)))
    case BooleanConstant(b) => List(Push(BooleanValue(b)))
    case NullExpression => List(Push(NilValue))
  }

  private def lambdaInstructions(name:Option[String], lambda:LambdaExpression): List[Instruction] ={
    val bodyInstructions = generate(lambda.body)
    List(PushClosure(name, lambda.bindings, findVariables(bodyInstructions).filter(!lambda.bindings.contains(_)), bodyInstructions))
  }

  private def findVariables(instructions: List[Instruction]):List[String] = {
    val definitions = ListBuffer[String]()
    instructions.collect{
      case Put(v) => {
        definitions += v
        List()
      }
      case Get(v) if !definitions.contains(v) => List(v)
      //case PushClosure(_, bindings, _, _) => bindings
    }.flatten.distinct
  }
}
