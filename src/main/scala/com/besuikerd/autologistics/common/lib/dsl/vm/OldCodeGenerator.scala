package com.besuikerd.autologistics.common.lib.dsl.vm

import java.util.{ArrayList, List => JList}
import com.besuikerd.autologistics.common.lib.dsl._
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.{Instruction, _}
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare._
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical._
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric._
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue._

import scala.collection.JavaConversions._

object OldCodeGenerator {
  def generate(statements:List[Statement]):JList[Instruction] = {
    val stack = new ArrayList[Instruction]()
    stack.add(OpenScopeInstruction.instance)
    for(s <- statements){
      generate(s, stack)
    }
    stack.add(CloseScopeInstruction.instance)
    stack
  }

  def generate(node: ASTNode, stack:JList[Instruction]): Unit = node match{
    case ExpressionStatement(e) => {generate(e, stack); stack.add(PopInstruction.instance)}
    case Assignment(binding, e, isLocal) => {
      e match{
        case l:LambdaExpression => lambdaInstructions(Some(binding), l, stack);
        case other => generate(other, stack)
      }
      stack.add(new PutInstruction(binding, isLocal))
    }
    case AssignField(objExp, fields, binding) => {
      generate(objExp, stack)

      val (init, last) = fields.splitAt(fields.length - 1)
      for(field <- init){
        stack.add(new PushInstruction(new StringValue(field)))
        stack.add(GetFieldInstruction.instance)
      }

      stack.add(new PushInstruction(new StringValue(last.head)))
      generate(binding, stack)
      stack.add(PutFieldInstruction.instance)
    }

    case AssignIndex(objExp, indexes, binding) => {
      generate(objExp, stack)

      val (init, last) = indexes.splitAt(indexes.length - 1)
      for(index <- init){
        generate(index, stack)
        stack.add(GetFieldInstruction.instance)
      }

      generate(last.head, stack)
      generate(binding, stack)
      stack.add(PutFieldInstruction.instance)
    }

    case WhileStatement(condition, body) => {
      val conditionStatements = new ArrayList[Instruction]()
      generate(condition, conditionStatements)
      val bodyStatements = new ArrayList[Instruction]()
      generate(body, bodyStatements)
      bodyStatements.add(new LoadInstruction(conditionStatements))
      stack.addAll(conditionStatements)
      stack.add(new RepeatedBranchInstruction(bodyStatements))
    }

    case VariableExpression(name) => stack.add(new GetInstruction(name))
    case l:LambdaExpression => lambdaInstructions(None, l, stack)
    case BlockExpression(body) => {
      stack.add(OpenScopeInstruction.instance)

      val statements = new ArrayList[Instruction]()
      for(statement <- body){
        generate(statement, statements)
      }

      if(statements.isEmpty){
        stack.add(new PushInstruction(NilValue.instance))
      } else{
        val lastIndex = statements.size() - 1
        val lastStatement = statements.get(lastIndex)
        if(lastStatement == PopInstruction.instance){
          statements.remove(lastIndex)
          stack.addAll(statements)
        } else{
          stack.addAll(statements)
          stack.add(new PushInstruction(NilValue.instance))
        }
      }
      stack.add(CloseScopeInstruction.instance)
    }

//    case Instructions(instructions) => for(instruction <- instructions){
//      stack.add(instruction)
//    }

    case IfElseExpression(condition, ifExp, elseExp) => {
      generate(condition, stack)
      val ifInstructions = new ArrayList[Instruction]()
      generate(ifExp, ifInstructions)
      val elseInstructions = new ArrayList[Instruction]()
      elseExp match{
        case Some(e) => generate(e, elseInstructions)
        case None => elseInstructions.add(new PushInstruction(NilValue.instance))
      }
      stack.add(new BranchInstruction(ifInstructions, elseInstructions))
    }

    case Application(e, args) => {
      generate(e, stack)
      for(arg <- args){
        generate(arg, stack)
      }
      stack.add(new CallInstruction(args.length))
    }

    case Add(e1, _, e2) => {generate(e1, stack); generate(e2, stack); stack.add(AddInstruction.instance);}
    case Sub(e1, _, e2) => {generate(e1, stack); generate(e2, stack); stack.add(SubInstruction.instance);}
    case Mul(e1, _, e2) => {generate(e1, stack); generate(e2, stack); stack.add(MulInstruction.instance);}
    case Div(e1, _, e2) => {generate(e1, stack); generate(e2, stack); stack.add(DivInstruction.instance);}
    case Mod(e1, _, e2) => {generate(e1, stack); generate(e2, stack); stack.add(ModInstruction.instance);}
    case GT(e1, _, e2) =>  {generate(e1, stack); generate(e2, stack); stack.add(GTInstruction.instance);}
    case GTE(e1, _, e2) => {generate(e1, stack); generate(e2, stack); stack.add(GTEInstruction.instance);}
    case LT(e1, _, e2) =>  {generate(e1, stack); generate(e2, stack); stack.add(LTInstruction.instance);}
    case LTE(e1, _, e2) => {generate(e1, stack); generate(e2, stack); stack.add(LTEInstruction.instance);}
    case EQ(e1, _, e2) =>  {generate(e1, stack); generate(e2, stack); stack.add(EQInstruction.instance);}
    case NEQ(e1, _, e2) => {generate(e1, stack); generate(e2, stack); stack.add(NEQInstruction.instance);}
    case And(e1, _, e2) => {generate(e1, stack); generate(e2, stack); stack.add(AndInstruction.instance);}
    case Or(e1, _, e2) =>  {generate(e1, stack); generate(e2, stack); stack.add(OrInstruction.instance);}

    case ObjectExpression(mapping) => {
      for(v <- mapping.values){
        generate(v, stack)
      }
      val keys = new ArrayList[String]()
      for(key <- mapping.keys) keys.add(0, key)
      stack.add(new PushObjectInstruction(keys))
    }

    case IndexExpression(exp, indexes) => {
      generate(exp, stack)
      for(index <- indexes) {
        generate(index, stack)
        stack.add(GetFieldInstruction.instance)
      }
    }

    case ObjectFieldExpression(exp, bindings) => generate(new IndexExpression(exp, bindings.map(StringLiteral)), stack)

    case ListExpression(values) => {
      for(value <- values.reverse){
        generate(value, stack)
      }
      stack.add(new PushListInstruction(values.length))
    }

    case NaturalNumberConstant(n) => stack.add(new PushInstruction(new IntegerValue(n)))
    case RealNumberConstant(n) => stack.add(new PushInstruction(new DecimalValue(n)))
    case StringLiteral(n) => stack.add(new PushInstruction(new StringValue(n)))
    case BooleanConstant(b) => stack.add(if(b) PushInstruction.pushTrue else PushInstruction.pushFalse)
    case NullExpression => stack.add(PushInstruction.pushNil)
  }

  private def lambdaInstructions(name:Option[String], lambda:LambdaExpression, stack:JList[Instruction]): Unit ={
    val bodyInstructions = new ArrayList[Instruction]()
    generate(lambda.body, bodyInstructions)
    val pushClosureInstruction = new PushClosureInstruction(name.orNull, seqAsJavaList(lambda.bindings), findVariables(lambda.bindings, bodyInstructions), bodyInstructions)
    stack.add(pushClosureInstruction)
  }

  private def findVariables(bindings: List[String], instructions: JList[Instruction]):JList[String] = {
    val variables = new ArrayList[String]()
    val definitions = new ArrayList[String]()
    instructions.foreach{
      case i: PutInstruction if !definitions.contains(i.name) && !bindings.contains(i.name) => definitions.add(i.name)
      case i: GetInstruction if !definitions.contains(i.name) && !bindings.contains(i.name) => variables.add(i.name)
      case _ =>
      //case PushClosure(_, bindings, _, _) => bindings
    }
    variables
  }
}
