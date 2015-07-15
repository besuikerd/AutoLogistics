// Generated from C:\Users\Nick\projects\minecraft\AutoLogistics\src\main\antlr\AutoLogistics.g4 by ANTLR 4.5

package com.besuikerd.autologistics.common.lib.antlr;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AutoLogisticsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AutoLogisticsVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(AutoLogisticsParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignLocalExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignLocalExp(AutoLogisticsParser.AssignLocalExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExp(AutoLogisticsParser.AndExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExp(AutoLogisticsParser.StringExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NullExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullExp(AutoLogisticsParser.NullExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignFieldExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignFieldExp(AutoLogisticsParser.AssignFieldExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BlockExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockExp(AutoLogisticsParser.BlockExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileExp(AutoLogisticsParser.WhileExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignIndexExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignIndexExp(AutoLogisticsParser.AssignIndexExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExp(AutoLogisticsParser.OrExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AppExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAppExp(AutoLogisticsParser.AppExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjectExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectExp(AutoLogisticsParser.ObjectExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSubExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExp(AutoLogisticsParser.AddSubExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDivModExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivModExp(AutoLogisticsParser.MulDivModExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DecimalExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimalExp(AutoLogisticsParser.DecimalExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntegerExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerExp(AutoLogisticsParser.IntegerExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompareExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareExp(AutoLogisticsParser.CompareExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LambdaExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExp(AutoLogisticsParser.LambdaExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CoordExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCoordExp(AutoLogisticsParser.CoordExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExp(AutoLogisticsParser.ListExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReferrableExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferrableExp(AutoLogisticsParser.ReferrableExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ItemExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItemExp(AutoLogisticsParser.ItemExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NegateExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateExp(AutoLogisticsParser.NegateExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExp(AutoLogisticsParser.AssignExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ItemFilter}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItemFilter(AutoLogisticsParser.ItemFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanExp(AutoLogisticsParser.BooleanExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfElseExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseExp(AutoLogisticsParser.IfElseExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransferExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransferExp(AutoLogisticsParser.TransferExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(AutoLogisticsParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#blockOrExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockOrExp(AutoLogisticsParser.BlockOrExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableExp(AutoLogisticsParser.VariableExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IndexExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexExp(AutoLogisticsParser.IndexExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExp(AutoLogisticsParser.ParenExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FieldExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldExp(AutoLogisticsParser.FieldExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#expList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpList(AutoLogisticsParser.ExpListContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#idList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdList(AutoLogisticsParser.IdListContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#kvList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKvList(AutoLogisticsParser.KvListContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#index}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(AutoLogisticsParser.IndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(AutoLogisticsParser.FieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#kv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKv(AutoLogisticsParser.KvContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#opMulDivMod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpMulDivMod(AutoLogisticsParser.OpMulDivModContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#opAddSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpAddSub(AutoLogisticsParser.OpAddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link AutoLogisticsParser#opCompare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpCompare(AutoLogisticsParser.OpCompareContext ctx);
}