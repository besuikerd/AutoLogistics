package com.besuikerd.autologistics.common.lib.dsl.parser

import com.besuikerd.autologistics.common.lib.dsl._

object DSLPrettyPrinter{

  def prettify(exp:Expression): String = prettifyLevelled(exp, 0)
  private def prettifyLevelled(exp:Expression, level:Int, withTabs:Boolean = true): String = {
    val tabs = if(withTabs) "\t" * level else ""
    exp match{
      case Application(expression, args) =>
        s"""${tabs}App(
            |${prettifyLevelled(expression, level + 1)}
            |${args.map(prettifyLevelled(_, level + 1)).mkString("\n")}
            |$tabs)""".stripMargin
      case VariableExpression(name) => tabs ++ s"Variable($name)"
      case NaturalNumberConstant(i) => tabs ++ i.toString
      case RealNumberConstant(i) => tabs ++ i.toString

      case b:BinaryExpression =>
        s"""${tabs}${b.getClass().getSimpleName()}(
           |${prettifyLevelled(b.e1, level + 1)}
           |${prettifyLevelled(b.e2, level + 1)}
           |$tabs)
         """.stripMargin
      case LambdaExpression(bindings, expression) =>
        s"""${tabs}Lambda(
            |${bindings.map(b => s"$tabs\t$b").mkString("\n")}
            |${prettifyLevelled(expression, level + 1)}
            |$tabs)""".stripMargin
      case BlockExpression(statements) =>
        s"""${tabs}Block{
          |${statements.map(prettifyLS(_, level + 1)).mkString("\n")}
          |$tabs}
        """.stripMargin
      case ListExpression(exps) =>
        s"""${tabs}List[
           |${exps.map(prettifyLevelled(_, level + 1)).mkString("\n")}
           |$tabs]
         """.stripMargin
      case x => tabs ++ x.toString
    }
  }


  def prettify(statement:Statement):String = prettifyLS(statement, 0)
  def prettifyLS(statement:Statement, level:Int, withTabs:Boolean = true): String = {
    val tabs = if(withTabs) "\t" * level else ""
    statement match {
      case ExpressionStatement(e) =>
        s"""${tabs}ExpressionStatement(
           |${prettifyLevelled(e, level + 1)}
           |$tabs)
         """.stripMargin
      case Assignment(binding, e, _) =>
        s"""${tabs}Assignment(
           |$tabs\t$binding
           |${prettifyLevelled(e, level + 1)}
           |$tabs)
         """.stripMargin
      case AssignField(obj, fields, binding) =>
        s"""${tabs}AssignField(
           |${prettifyLevelled(obj, level + 1)}
           |$tabs${fields.mkString("\n")}
           |${prettifyLevelled(binding, level + 1)}
           |$tabs)
         """.stripMargin
      case WhileStatement(pred, e) =>
        s"""${tabs}WhileStatement(
           |${prettifyLevelled(pred, level + 1)}
           |${prettifyLS(e, level + 1)}
           |$tabs)
         """.stripMargin
      case other => tabs ++ other.toString
    }
  }
}