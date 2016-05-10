// Generated from /home/nick/projects/minecraft/AutoLogistics/src/main/antlr/AutoLogistics.g4 by ANTLR 4.5

package com.besuikerd.autologistics.common.lib.antlr;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AutoLogisticsParser}.
 */
public interface AutoLogisticsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(AutoLogisticsParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(AutoLogisticsParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignLocalExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAssignLocalExp(AutoLogisticsParser.AssignLocalExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignLocalExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAssignLocalExp(AutoLogisticsParser.AssignLocalExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAndExp(AutoLogisticsParser.AndExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAndExp(AutoLogisticsParser.AndExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterStringExp(AutoLogisticsParser.StringExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitStringExp(AutoLogisticsParser.StringExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NullExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNullExp(AutoLogisticsParser.NullExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NullExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNullExp(AutoLogisticsParser.NullExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignFieldExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAssignFieldExp(AutoLogisticsParser.AssignFieldExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignFieldExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAssignFieldExp(AutoLogisticsParser.AssignFieldExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BlockExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBlockExp(AutoLogisticsParser.BlockExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BlockExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBlockExp(AutoLogisticsParser.BlockExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterWhileExp(AutoLogisticsParser.WhileExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitWhileExp(AutoLogisticsParser.WhileExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignIndexExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAssignIndexExp(AutoLogisticsParser.AssignIndexExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignIndexExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAssignIndexExp(AutoLogisticsParser.AssignIndexExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterOrExp(AutoLogisticsParser.OrExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitOrExp(AutoLogisticsParser.OrExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AppExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAppExp(AutoLogisticsParser.AppExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AppExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAppExp(AutoLogisticsParser.AppExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObjectExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterObjectExp(AutoLogisticsParser.ObjectExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObjectExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitObjectExp(AutoLogisticsParser.ObjectExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSubExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExp(AutoLogisticsParser.AddSubExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSubExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExp(AutoLogisticsParser.AddSubExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDivModExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterMulDivModExp(AutoLogisticsParser.MulDivModExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDivModExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitMulDivModExp(AutoLogisticsParser.MulDivModExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DecimalExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterDecimalExp(AutoLogisticsParser.DecimalExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DecimalExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitDecimalExp(AutoLogisticsParser.DecimalExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntegerExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIntegerExp(AutoLogisticsParser.IntegerExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIntegerExp(AutoLogisticsParser.IntegerExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompareExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterCompareExp(AutoLogisticsParser.CompareExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompareExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitCompareExp(AutoLogisticsParser.CompareExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LambdaExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExp(AutoLogisticsParser.LambdaExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LambdaExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExp(AutoLogisticsParser.LambdaExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CoordExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterCoordExp(AutoLogisticsParser.CoordExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CoordExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitCoordExp(AutoLogisticsParser.CoordExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterListExp(AutoLogisticsParser.ListExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitListExp(AutoLogisticsParser.ListExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReferrableExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterReferrableExp(AutoLogisticsParser.ReferrableExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReferrableExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitReferrableExp(AutoLogisticsParser.ReferrableExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ItemExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterItemExp(AutoLogisticsParser.ItemExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ItemExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitItemExp(AutoLogisticsParser.ItemExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NegateExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNegateExp(AutoLogisticsParser.NegateExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NegateExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNegateExp(AutoLogisticsParser.NegateExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAssignExp(AutoLogisticsParser.AssignExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAssignExp(AutoLogisticsParser.AssignExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ItemFilter}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterItemFilter(AutoLogisticsParser.ItemFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ItemFilter}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitItemFilter(AutoLogisticsParser.ItemFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExp(AutoLogisticsParser.BooleanExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExp(AutoLogisticsParser.BooleanExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfElseExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIfElseExp(AutoLogisticsParser.IfElseExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfElseExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIfElseExp(AutoLogisticsParser.IfElseExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TransferExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterTransferExp(AutoLogisticsParser.TransferExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TransferExp}
	 * labeled alternative in {@link AutoLogisticsParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitTransferExp(AutoLogisticsParser.TransferExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(AutoLogisticsParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(AutoLogisticsParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#blockOrExp}.
	 * @param ctx the parse tree
	 */
	void enterBlockOrExp(AutoLogisticsParser.BlockOrExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#blockOrExp}.
	 * @param ctx the parse tree
	 */
	void exitBlockOrExp(AutoLogisticsParser.BlockOrExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 */
	void enterVariableExp(AutoLogisticsParser.VariableExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 */
	void exitVariableExp(AutoLogisticsParser.VariableExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IndexExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 */
	void enterIndexExp(AutoLogisticsParser.IndexExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IndexExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 */
	void exitIndexExp(AutoLogisticsParser.IndexExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 */
	void enterParenExp(AutoLogisticsParser.ParenExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 */
	void exitParenExp(AutoLogisticsParser.ParenExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FieldExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 */
	void enterFieldExp(AutoLogisticsParser.FieldExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FieldExp}
	 * labeled alternative in {@link AutoLogisticsParser#referrable}.
	 * @param ctx the parse tree
	 */
	void exitFieldExp(AutoLogisticsParser.FieldExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#expList}.
	 * @param ctx the parse tree
	 */
	void enterExpList(AutoLogisticsParser.ExpListContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#expList}.
	 * @param ctx the parse tree
	 */
	void exitExpList(AutoLogisticsParser.ExpListContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#idList}.
	 * @param ctx the parse tree
	 */
	void enterIdList(AutoLogisticsParser.IdListContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#idList}.
	 * @param ctx the parse tree
	 */
	void exitIdList(AutoLogisticsParser.IdListContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#kvList}.
	 * @param ctx the parse tree
	 */
	void enterKvList(AutoLogisticsParser.KvListContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#kvList}.
	 * @param ctx the parse tree
	 */
	void exitKvList(AutoLogisticsParser.KvListContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#index}.
	 * @param ctx the parse tree
	 */
	void enterIndex(AutoLogisticsParser.IndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#index}.
	 * @param ctx the parse tree
	 */
	void exitIndex(AutoLogisticsParser.IndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(AutoLogisticsParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(AutoLogisticsParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#kv}.
	 * @param ctx the parse tree
	 */
	void enterKv(AutoLogisticsParser.KvContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#kv}.
	 * @param ctx the parse tree
	 */
	void exitKv(AutoLogisticsParser.KvContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#opMulDivMod}.
	 * @param ctx the parse tree
	 */
	void enterOpMulDivMod(AutoLogisticsParser.OpMulDivModContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#opMulDivMod}.
	 * @param ctx the parse tree
	 */
	void exitOpMulDivMod(AutoLogisticsParser.OpMulDivModContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#opAddSub}.
	 * @param ctx the parse tree
	 */
	void enterOpAddSub(AutoLogisticsParser.OpAddSubContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#opAddSub}.
	 * @param ctx the parse tree
	 */
	void exitOpAddSub(AutoLogisticsParser.OpAddSubContext ctx);
	/**
	 * Enter a parse tree produced by {@link AutoLogisticsParser#opCompare}.
	 * @param ctx the parse tree
	 */
	void enterOpCompare(AutoLogisticsParser.OpCompareContext ctx);
	/**
	 * Exit a parse tree produced by {@link AutoLogisticsParser#opCompare}.
	 * @param ctx the parse tree
	 */
	void exitOpCompare(AutoLogisticsParser.OpCompareContext ctx);
}