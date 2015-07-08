// Generated from C:\Users\Nick\projects\minecraft\AutoLogistics\src\main\antlr\AutoLogistics.g4 by ANTLR 4.5

package com.besuikerd.autologistics.common.lib.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AutoLogisticsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TRUE=1, FALSE=2, NULL=3, IF=4, ELSE=5, WHILE=6, LOCAL=7, LPAREN=8, RPAREN=9, 
		LBRACKET=10, RBRACKET=11, LCURLY=12, RCURLY=13, COLON=14, SEMICOLON=15, 
		COMMA=16, ADD=17, SUB=18, MUL=19, DIV=20, MOD=21, GT=22, GTE=23, LT=24, 
		LTE=25, EQ=26, NEQ=27, AND=28, OR=29, AT=30, NOT=31, TILDE=32, QUOTE=33, 
		BECOMES=34, DOT=35, BACKSLASH=36, ARROWRIGHT=37, TRANSFER=38, Integer=39, 
		Decimal=40, Identifier=41, Item=42, StringLiteral=43, Comment=44, LineComment=45, 
		Ws=46;
	public static final int
		RULE_program = 0, RULE_exp = 1, RULE_block = 2, RULE_blockOrExp = 3, RULE_expList = 4, 
		RULE_idList = 5, RULE_kvList = 6, RULE_index = 7, RULE_field = 8, RULE_kv = 9, 
		RULE_opMulDivMod = 10, RULE_opAddSub = 11, RULE_opCompare = 12;
	public static final String[] ruleNames = {
		"program", "exp", "block", "blockOrExp", "expList", "idList", "kvList", 
		"index", "field", "kv", "opMulDivMod", "opAddSub", "opCompare"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'true'", "'false'", "'null'", "'if'", "'else'", "'while'", "'local'", 
		"'('", "')'", "'['", "']'", "'{'", "'}'", "':'", "';'", "','", "'+'", 
		"'-'", "'*'", "'/'", "'%'", "'>'", "'<='", "'<'", "'>='", "'=='", "'!='", 
		"'&&'", "'||'", "'@'", "'!'", "'~'", "'\"'", "'='", "'.'", "'\\'", "'->'", 
		"'>>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "TRUE", "FALSE", "NULL", "IF", "ELSE", "WHILE", "LOCAL", "LPAREN", 
		"RPAREN", "LBRACKET", "RBRACKET", "LCURLY", "RCURLY", "COLON", "SEMICOLON", 
		"COMMA", "ADD", "SUB", "MUL", "DIV", "MOD", "GT", "GTE", "LT", "LTE", 
		"EQ", "NEQ", "AND", "OR", "AT", "NOT", "TILDE", "QUOTE", "BECOMES", "DOT", 
		"BACKSLASH", "ARROWRIGHT", "TRANSFER", "Integer", "Decimal", "Identifier", 
		"Item", "StringLiteral", "Comment", "LineComment", "Ws"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "AutoLogistics.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AutoLogisticsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(AutoLogisticsParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(AutoLogisticsParser.SEMICOLON, i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
				{
				{
				setState(26);
				exp(0);
				setState(28);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(27);
					match(SEMICOLON);
					}
				}

				}
				}
				setState(34);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssignLocalExpContext extends ExpContext {
		public TerminalNode LOCAL() { return getToken(AutoLogisticsParser.LOCAL, 0); }
		public TerminalNode Identifier() { return getToken(AutoLogisticsParser.Identifier, 0); }
		public TerminalNode BECOMES() { return getToken(AutoLogisticsParser.BECOMES, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public AssignLocalExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterAssignLocalExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitAssignLocalExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitAssignLocalExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode AND() { return getToken(AutoLogisticsParser.AND, 0); }
		public AndExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterAndExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitAndExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitAndExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringExpContext extends ExpContext {
		public TerminalNode StringLiteral() { return getToken(AutoLogisticsParser.StringLiteral, 0); }
		public StringExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterStringExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitStringExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitStringExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IndexExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public IndexExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterIndexExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitIndexExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitIndexExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NullExpContext extends ExpContext {
		public TerminalNode NULL() { return getToken(AutoLogisticsParser.NULL, 0); }
		public NullExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterNullExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitNullExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitNullExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignFieldExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode BECOMES() { return getToken(AutoLogisticsParser.BECOMES, 0); }
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public AssignFieldExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterAssignFieldExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitAssignFieldExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitAssignFieldExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlockExpContext extends ExpContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterBlockExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitBlockExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitBlockExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileExpContext extends ExpContext {
		public TerminalNode WHILE() { return getToken(AutoLogisticsParser.WHILE, 0); }
		public List<BlockOrExpContext> blockOrExp() {
			return getRuleContexts(BlockOrExpContext.class);
		}
		public BlockOrExpContext blockOrExp(int i) {
			return getRuleContext(BlockOrExpContext.class,i);
		}
		public WhileExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterWhileExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitWhileExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitWhileExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignIndexExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode BECOMES() { return getToken(AutoLogisticsParser.BECOMES, 0); }
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public AssignIndexExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterAssignIndexExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitAssignIndexExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitAssignIndexExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode OR() { return getToken(AutoLogisticsParser.OR, 0); }
		public OrExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterOrExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitOrExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitOrExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AppExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(AutoLogisticsParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(AutoLogisticsParser.RPAREN, 0); }
		public ExpListContext expList() {
			return getRuleContext(ExpListContext.class,0);
		}
		public AppExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterAppExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitAppExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitAppExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ObjectExpContext extends ExpContext {
		public TerminalNode LCURLY() { return getToken(AutoLogisticsParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(AutoLogisticsParser.RCURLY, 0); }
		public KvListContext kvList() {
			return getRuleContext(KvListContext.class,0);
		}
		public ObjectExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterObjectExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitObjectExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitObjectExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FieldExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public FieldExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterFieldExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitFieldExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitFieldExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddSubExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OpAddSubContext opAddSub() {
			return getRuleContext(OpAddSubContext.class,0);
		}
		public AddSubExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterAddSubExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitAddSubExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitAddSubExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MulDivModExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OpMulDivModContext opMulDivMod() {
			return getRuleContext(OpMulDivModContext.class,0);
		}
		public MulDivModExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterMulDivModExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitMulDivModExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitMulDivModExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DecimalExpContext extends ExpContext {
		public TerminalNode Decimal() { return getToken(AutoLogisticsParser.Decimal, 0); }
		public DecimalExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterDecimalExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitDecimalExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitDecimalExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerExpContext extends ExpContext {
		public TerminalNode Integer() { return getToken(AutoLogisticsParser.Integer, 0); }
		public IntegerExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterIntegerExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitIntegerExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitIntegerExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CompareExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OpCompareContext opCompare() {
			return getRuleContext(OpCompareContext.class,0);
		}
		public CompareExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterCompareExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitCompareExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitCompareExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LambdaExpContext extends ExpContext {
		public TerminalNode BACKSLASH() { return getToken(AutoLogisticsParser.BACKSLASH, 0); }
		public TerminalNode ARROWRIGHT() { return getToken(AutoLogisticsParser.ARROWRIGHT, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public IdListContext idList() {
			return getRuleContext(IdListContext.class,0);
		}
		public LambdaExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterLambdaExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitLambdaExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitLambdaExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CoordExpContext extends ExpContext {
		public TerminalNode LPAREN() { return getToken(AutoLogisticsParser.LPAREN, 0); }
		public List<TerminalNode> Integer() { return getTokens(AutoLogisticsParser.Integer); }
		public TerminalNode Integer(int i) {
			return getToken(AutoLogisticsParser.Integer, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AutoLogisticsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AutoLogisticsParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(AutoLogisticsParser.RPAREN, 0); }
		public TerminalNode TILDE() { return getToken(AutoLogisticsParser.TILDE, 0); }
		public CoordExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterCoordExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitCoordExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitCoordExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ListExpContext extends ExpContext {
		public TerminalNode LBRACKET() { return getToken(AutoLogisticsParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(AutoLogisticsParser.RBRACKET, 0); }
		public ExpListContext expList() {
			return getRuleContext(ExpListContext.class,0);
		}
		public ListExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterListExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitListExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitListExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ItemExpContext extends ExpContext {
		public TerminalNode Item() { return getToken(AutoLogisticsParser.Item, 0); }
		public ItemExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterItemExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitItemExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitItemExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegateExpContext extends ExpContext {
		public TerminalNode NOT() { return getToken(AutoLogisticsParser.NOT, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public NegateExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterNegateExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitNegateExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitNegateExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignExpContext extends ExpContext {
		public TerminalNode Identifier() { return getToken(AutoLogisticsParser.Identifier, 0); }
		public TerminalNode BECOMES() { return getToken(AutoLogisticsParser.BECOMES, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public AssignExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterAssignExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitAssignExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitAssignExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableExpContext extends ExpContext {
		public TerminalNode Identifier() { return getToken(AutoLogisticsParser.Identifier, 0); }
		public VariableExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterVariableExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitVariableExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitVariableExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ItemFilterContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode AT() { return getToken(AutoLogisticsParser.AT, 0); }
		public ItemFilterContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterItemFilter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitItemFilter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitItemFilter(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExpContext extends ExpContext {
		public TerminalNode LPAREN() { return getToken(AutoLogisticsParser.LPAREN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(AutoLogisticsParser.RPAREN, 0); }
		public ParenExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterParenExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitParenExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitParenExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanExpContext extends ExpContext {
		public TerminalNode TRUE() { return getToken(AutoLogisticsParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(AutoLogisticsParser.FALSE, 0); }
		public BooleanExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterBooleanExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitBooleanExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitBooleanExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfElseExpContext extends ExpContext {
		public TerminalNode IF() { return getToken(AutoLogisticsParser.IF, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(AutoLogisticsParser.ELSE, 0); }
		public IfElseExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterIfElseExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitIfElseExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitIfElseExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TransferExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode TRANSFER() { return getToken(AutoLogisticsParser.TRANSFER, 0); }
		public TransferExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterTransferExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitTransferExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitTransferExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				_localctx = new NegateExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(36);
				match(NOT);
				setState(37);
				exp(19);
				}
				break;
			case 2:
				{
				_localctx = new LambdaExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(38);
				match(BACKSLASH);
				setState(40);
				_la = _input.LA(1);
				if (_la==Identifier) {
					{
					setState(39);
					idList();
					}
				}

				setState(42);
				match(ARROWRIGHT);
				setState(43);
				exp(13);
				}
				break;
			case 3:
				{
				_localctx = new AssignExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(44);
				match(Identifier);
				setState(45);
				match(BECOMES);
				setState(46);
				exp(6);
				}
				break;
			case 4:
				{
				_localctx = new AssignLocalExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(47);
				match(LOCAL);
				setState(48);
				match(Identifier);
				setState(49);
				match(BECOMES);
				setState(50);
				exp(5);
				}
				break;
			case 5:
				{
				_localctx = new IntegerExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(51);
				match(Integer);
				}
				break;
			case 6:
				{
				_localctx = new DecimalExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(52);
				match(Decimal);
				}
				break;
			case 7:
				{
				_localctx = new BooleanExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(53);
				_la = _input.LA(1);
				if ( !(_la==TRUE || _la==FALSE) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 8:
				{
				_localctx = new StringExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(54);
				match(StringLiteral);
				}
				break;
			case 9:
				{
				_localctx = new NullExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(55);
				match(NULL);
				}
				break;
			case 10:
				{
				_localctx = new ParenExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(56);
				match(LPAREN);
				setState(57);
				exp(0);
				setState(58);
				match(RPAREN);
				}
				break;
			case 11:
				{
				_localctx = new IfElseExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(60);
				match(IF);
				setState(61);
				exp(0);
				setState(62);
				exp(0);
				setState(65);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(63);
					match(ELSE);
					setState(64);
					exp(0);
					}
					break;
				}
				}
				break;
			case 12:
				{
				_localctx = new ListExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(67);
				match(LBRACKET);
				setState(69);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
					{
					setState(68);
					expList();
					}
				}

				setState(71);
				match(RBRACKET);
				}
				break;
			case 13:
				{
				_localctx = new ObjectExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(72);
				match(LCURLY);
				setState(74);
				_la = _input.LA(1);
				if (_la==Identifier) {
					{
					setState(73);
					kvList();
					}
				}

				setState(76);
				match(RCURLY);
				}
				break;
			case 14:
				{
				_localctx = new BlockExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(77);
				block();
				}
				break;
			case 15:
				{
				_localctx = new ItemExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(78);
				match(Item);
				}
				break;
			case 16:
				{
				_localctx = new CoordExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(80);
				_la = _input.LA(1);
				if (_la==TILDE) {
					{
					setState(79);
					match(TILDE);
					}
				}

				setState(82);
				match(LPAREN);
				setState(83);
				match(Integer);
				setState(84);
				match(COMMA);
				setState(85);
				match(Integer);
				setState(86);
				match(COMMA);
				setState(87);
				match(Integer);
				setState(88);
				match(RPAREN);
				}
				break;
			case 17:
				{
				_localctx = new WhileExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(89);
				match(WHILE);
				setState(90);
				blockOrExp();
				setState(91);
				blockOrExp();
				}
				break;
			case 18:
				{
				_localctx = new VariableExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(93);
				match(Identifier);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(158);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(156);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new MulDivModExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(96);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(97);
						opMulDivMod();
						setState(98);
						exp(19);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(100);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(101);
						opAddSub();
						setState(102);
						exp(18);
						}
						break;
					case 3:
						{
						_localctx = new CompareExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(104);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(105);
						opCompare();
						setState(106);
						exp(17);
						}
						break;
					case 4:
						{
						_localctx = new AndExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(108);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(109);
						match(AND);
						setState(110);
						exp(16);
						}
						break;
					case 5:
						{
						_localctx = new OrExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(111);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(112);
						match(OR);
						setState(113);
						exp(15);
						}
						break;
					case 6:
						{
						_localctx = new ItemFilterContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(114);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(115);
						match(AT);
						setState(116);
						exp(11);
						}
						break;
					case 7:
						{
						_localctx = new TransferExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(117);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(118);
						match(TRANSFER);
						setState(119);
						exp(10);
						}
						break;
					case 8:
						{
						_localctx = new AssignIndexExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(120);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(122); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(121);
							index();
							}
							}
							setState(124); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==LBRACKET );
						setState(126);
						match(BECOMES);
						setState(127);
						exp(4);
						}
						break;
					case 9:
						{
						_localctx = new AssignFieldExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(129);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(131); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(130);
							field();
							}
							}
							setState(133); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==DOT );
						setState(135);
						match(BECOMES);
						setState(136);
						exp(3);
						}
						break;
					case 10:
						{
						_localctx = new AppExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(138);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(139);
						match(LPAREN);
						setState(141);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
							{
							setState(140);
							expList();
							}
						}

						setState(143);
						match(RPAREN);
						}
						break;
					case 11:
						{
						_localctx = new IndexExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(144);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(146); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(145);
								index();
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(148); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case 12:
						{
						_localctx = new FieldExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(150);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(152); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(151);
								field();
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(154); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					}
					} 
				}
				setState(160);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(AutoLogisticsParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(AutoLogisticsParser.RCURLY, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(AutoLogisticsParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(AutoLogisticsParser.SEMICOLON, i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(LCURLY);
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
				{
				{
				setState(162);
				exp(0);
				setState(164);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(163);
					match(SEMICOLON);
					}
				}

				}
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(171);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockOrExpContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BlockOrExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockOrExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterBlockOrExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitBlockOrExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitBlockOrExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockOrExpContext blockOrExp() throws RecognitionException {
		BlockOrExpContext _localctx = new BlockOrExpContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_blockOrExp);
		try {
			setState(175);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(173);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(174);
				exp(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpListContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AutoLogisticsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AutoLogisticsParser.COMMA, i);
		}
		public ExpListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterExpList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitExpList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitExpList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpListContext expList() throws RecognitionException {
		ExpListContext _localctx = new ExpListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			exp(0);
			setState(184);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << COMMA) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
				{
				{
				setState(179);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(178);
					match(COMMA);
					}
				}

				setState(181);
				exp(0);
				}
				}
				setState(186);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdListContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(AutoLogisticsParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(AutoLogisticsParser.Identifier, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AutoLogisticsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AutoLogisticsParser.COMMA, i);
		}
		public IdListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterIdList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitIdList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitIdList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdListContext idList() throws RecognitionException {
		IdListContext _localctx = new IdListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_idList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(Identifier);
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA || _la==Identifier) {
				{
				{
				setState(189);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(188);
					match(COMMA);
					}
				}

				setState(191);
				match(Identifier);
				}
				}
				setState(196);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KvListContext extends ParserRuleContext {
		public List<KvContext> kv() {
			return getRuleContexts(KvContext.class);
		}
		public KvContext kv(int i) {
			return getRuleContext(KvContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AutoLogisticsParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AutoLogisticsParser.COMMA, i);
		}
		public KvListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kvList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterKvList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitKvList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitKvList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KvListContext kvList() throws RecognitionException {
		KvListContext _localctx = new KvListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_kvList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			kv();
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA || _la==Identifier) {
				{
				{
				setState(199);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(198);
					match(COMMA);
					}
				}

				setState(201);
				kv();
				}
				}
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexContext extends ParserRuleContext {
		public TerminalNode LBRACKET() { return getToken(AutoLogisticsParser.LBRACKET, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(AutoLogisticsParser.RBRACKET, 0); }
		public IndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexContext index() throws RecognitionException {
		IndexContext _localctx = new IndexContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			match(LBRACKET);
			setState(208);
			exp(0);
			setState(209);
			match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(AutoLogisticsParser.DOT, 0); }
		public TerminalNode Identifier() { return getToken(AutoLogisticsParser.Identifier, 0); }
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			match(DOT);
			setState(212);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KvContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(AutoLogisticsParser.Identifier, 0); }
		public TerminalNode BECOMES() { return getToken(AutoLogisticsParser.BECOMES, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public KvContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kv; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterKv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitKv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitKv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KvContext kv() throws RecognitionException {
		KvContext _localctx = new KvContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_kv);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			match(Identifier);
			setState(215);
			match(BECOMES);
			setState(216);
			exp(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpMulDivModContext extends ParserRuleContext {
		public TerminalNode MUL() { return getToken(AutoLogisticsParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(AutoLogisticsParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(AutoLogisticsParser.MOD, 0); }
		public OpMulDivModContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opMulDivMod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterOpMulDivMod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitOpMulDivMod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitOpMulDivMod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpMulDivModContext opMulDivMod() throws RecognitionException {
		OpMulDivModContext _localctx = new OpMulDivModContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_opMulDivMod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpAddSubContext extends ParserRuleContext {
		public TerminalNode ADD() { return getToken(AutoLogisticsParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(AutoLogisticsParser.SUB, 0); }
		public OpAddSubContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opAddSub; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterOpAddSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitOpAddSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitOpAddSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpAddSubContext opAddSub() throws RecognitionException {
		OpAddSubContext _localctx = new OpAddSubContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_opAddSub);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			_la = _input.LA(1);
			if ( !(_la==ADD || _la==SUB) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpCompareContext extends ParserRuleContext {
		public TerminalNode GT() { return getToken(AutoLogisticsParser.GT, 0); }
		public TerminalNode GTE() { return getToken(AutoLogisticsParser.GTE, 0); }
		public TerminalNode LT() { return getToken(AutoLogisticsParser.LT, 0); }
		public TerminalNode LTE() { return getToken(AutoLogisticsParser.LTE, 0); }
		public TerminalNode EQ() { return getToken(AutoLogisticsParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(AutoLogisticsParser.NEQ, 0); }
		public OpCompareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opCompare; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterOpCompare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitOpCompare(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitOpCompare(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpCompareContext opCompare() throws RecognitionException {
		OpCompareContext _localctx = new OpCompareContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_opCompare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GTE) | (1L << LT) | (1L << LTE) | (1L << EQ) | (1L << NEQ))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 18);
		case 1:
			return precpred(_ctx, 17);
		case 2:
			return precpred(_ctx, 16);
		case 3:
			return precpred(_ctx, 15);
		case 4:
			return precpred(_ctx, 14);
		case 5:
			return precpred(_ctx, 10);
		case 6:
			return precpred(_ctx, 9);
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 2);
		case 9:
			return precpred(_ctx, 24);
		case 10:
			return precpred(_ctx, 8);
		case 11:
			return precpred(_ctx, 7);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\60\u00e3\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\5\2\37\n\2\7\2!\n\2\f\2\16\2$"+
		"\13\2\3\3\3\3\3\3\3\3\3\3\5\3+\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3D\n\3\3\3"+
		"\3\3\5\3H\n\3\3\3\3\3\3\3\5\3M\n\3\3\3\3\3\3\3\3\3\5\3S\n\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3a\n\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\6\3}\n\3\r\3\16\3~\3\3\3\3\3\3\3\3\3\3\6\3\u0086\n\3\r\3"+
		"\16\3\u0087\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u0090\n\3\3\3\3\3\3\3\6\3\u0095"+
		"\n\3\r\3\16\3\u0096\3\3\3\3\6\3\u009b\n\3\r\3\16\3\u009c\7\3\u009f\n\3"+
		"\f\3\16\3\u00a2\13\3\3\4\3\4\3\4\5\4\u00a7\n\4\7\4\u00a9\n\4\f\4\16\4"+
		"\u00ac\13\4\3\4\3\4\3\5\3\5\5\5\u00b2\n\5\3\6\3\6\5\6\u00b6\n\6\3\6\7"+
		"\6\u00b9\n\6\f\6\16\6\u00bc\13\6\3\7\3\7\5\7\u00c0\n\7\3\7\7\7\u00c3\n"+
		"\7\f\7\16\7\u00c6\13\7\3\b\3\b\5\b\u00ca\n\b\3\b\7\b\u00cd\n\b\f\b\16"+
		"\b\u00d0\13\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\16\3\16\3\16\2\3\4\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\6"+
		"\3\2\3\4\3\2\25\27\3\2\23\24\3\2\30\35\u0107\2\"\3\2\2\2\4`\3\2\2\2\6"+
		"\u00a3\3\2\2\2\b\u00b1\3\2\2\2\n\u00b3\3\2\2\2\f\u00bd\3\2\2\2\16\u00c7"+
		"\3\2\2\2\20\u00d1\3\2\2\2\22\u00d5\3\2\2\2\24\u00d8\3\2\2\2\26\u00dc\3"+
		"\2\2\2\30\u00de\3\2\2\2\32\u00e0\3\2\2\2\34\36\5\4\3\2\35\37\7\21\2\2"+
		"\36\35\3\2\2\2\36\37\3\2\2\2\37!\3\2\2\2 \34\3\2\2\2!$\3\2\2\2\" \3\2"+
		"\2\2\"#\3\2\2\2#\3\3\2\2\2$\"\3\2\2\2%&\b\3\1\2&\'\7!\2\2\'a\5\4\3\25"+
		"(*\7&\2\2)+\5\f\7\2*)\3\2\2\2*+\3\2\2\2+,\3\2\2\2,-\7\'\2\2-a\5\4\3\17"+
		"./\7+\2\2/\60\7$\2\2\60a\5\4\3\b\61\62\7\t\2\2\62\63\7+\2\2\63\64\7$\2"+
		"\2\64a\5\4\3\7\65a\7)\2\2\66a\7*\2\2\67a\t\2\2\28a\7-\2\29a\7\5\2\2:;"+
		"\7\n\2\2;<\5\4\3\2<=\7\13\2\2=a\3\2\2\2>?\7\6\2\2?@\5\4\3\2@C\5\4\3\2"+
		"AB\7\7\2\2BD\5\4\3\2CA\3\2\2\2CD\3\2\2\2Da\3\2\2\2EG\7\f\2\2FH\5\n\6\2"+
		"GF\3\2\2\2GH\3\2\2\2HI\3\2\2\2Ia\7\r\2\2JL\7\16\2\2KM\5\16\b\2LK\3\2\2"+
		"\2LM\3\2\2\2MN\3\2\2\2Na\7\17\2\2Oa\5\6\4\2Pa\7,\2\2QS\7\"\2\2RQ\3\2\2"+
		"\2RS\3\2\2\2ST\3\2\2\2TU\7\n\2\2UV\7)\2\2VW\7\22\2\2WX\7)\2\2XY\7\22\2"+
		"\2YZ\7)\2\2Za\7\13\2\2[\\\7\b\2\2\\]\5\b\5\2]^\5\b\5\2^a\3\2\2\2_a\7+"+
		"\2\2`%\3\2\2\2`(\3\2\2\2`.\3\2\2\2`\61\3\2\2\2`\65\3\2\2\2`\66\3\2\2\2"+
		"`\67\3\2\2\2`8\3\2\2\2`9\3\2\2\2`:\3\2\2\2`>\3\2\2\2`E\3\2\2\2`J\3\2\2"+
		"\2`O\3\2\2\2`P\3\2\2\2`R\3\2\2\2`[\3\2\2\2`_\3\2\2\2a\u00a0\3\2\2\2bc"+
		"\f\24\2\2cd\5\26\f\2de\5\4\3\25e\u009f\3\2\2\2fg\f\23\2\2gh\5\30\r\2h"+
		"i\5\4\3\24i\u009f\3\2\2\2jk\f\22\2\2kl\5\32\16\2lm\5\4\3\23m\u009f\3\2"+
		"\2\2no\f\21\2\2op\7\36\2\2p\u009f\5\4\3\22qr\f\20\2\2rs\7\37\2\2s\u009f"+
		"\5\4\3\21tu\f\f\2\2uv\7 \2\2v\u009f\5\4\3\rwx\f\13\2\2xy\7(\2\2y\u009f"+
		"\5\4\3\fz|\f\5\2\2{}\5\20\t\2|{\3\2\2\2}~\3\2\2\2~|\3\2\2\2~\177\3\2\2"+
		"\2\177\u0080\3\2\2\2\u0080\u0081\7$\2\2\u0081\u0082\5\4\3\6\u0082\u009f"+
		"\3\2\2\2\u0083\u0085\f\4\2\2\u0084\u0086\5\22\n\2\u0085\u0084\3\2\2\2"+
		"\u0086\u0087\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089"+
		"\3\2\2\2\u0089\u008a\7$\2\2\u008a\u008b\5\4\3\5\u008b\u009f\3\2\2\2\u008c"+
		"\u008d\f\32\2\2\u008d\u008f\7\n\2\2\u008e\u0090\5\n\6\2\u008f\u008e\3"+
		"\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u009f\7\13\2\2\u0092"+
		"\u0094\f\n\2\2\u0093\u0095\5\20\t\2\u0094\u0093\3\2\2\2\u0095\u0096\3"+
		"\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u009f\3\2\2\2\u0098"+
		"\u009a\f\t\2\2\u0099\u009b\5\22\n\2\u009a\u0099\3\2\2\2\u009b\u009c\3"+
		"\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2\2\2\u009e"+
		"b\3\2\2\2\u009ef\3\2\2\2\u009ej\3\2\2\2\u009en\3\2\2\2\u009eq\3\2\2\2"+
		"\u009et\3\2\2\2\u009ew\3\2\2\2\u009ez\3\2\2\2\u009e\u0083\3\2\2\2\u009e"+
		"\u008c\3\2\2\2\u009e\u0092\3\2\2\2\u009e\u0098\3\2\2\2\u009f\u00a2\3\2"+
		"\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\5\3\2\2\2\u00a2\u00a0"+
		"\3\2\2\2\u00a3\u00aa\7\16\2\2\u00a4\u00a6\5\4\3\2\u00a5\u00a7\7\21\2\2"+
		"\u00a6\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a9\3\2\2\2\u00a8\u00a4"+
		"\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab"+
		"\u00ad\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\7\17\2\2\u00ae\7\3\2\2"+
		"\2\u00af\u00b2\5\6\4\2\u00b0\u00b2\5\4\3\2\u00b1\u00af\3\2\2\2\u00b1\u00b0"+
		"\3\2\2\2\u00b2\t\3\2\2\2\u00b3\u00ba\5\4\3\2\u00b4\u00b6\7\22\2\2\u00b5"+
		"\u00b4\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b9\5\4"+
		"\3\2\u00b8\u00b5\3\2\2\2\u00b9\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba"+
		"\u00bb\3\2\2\2\u00bb\13\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bd\u00c4\7+\2\2"+
		"\u00be\u00c0\7\22\2\2\u00bf\u00be\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c1"+
		"\3\2\2\2\u00c1\u00c3\7+\2\2\u00c2\u00bf\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4"+
		"\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\r\3\2\2\2\u00c6\u00c4\3\2\2\2"+
		"\u00c7\u00ce\5\24\13\2\u00c8\u00ca\7\22\2\2\u00c9\u00c8\3\2\2\2\u00c9"+
		"\u00ca\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cd\5\24\13\2\u00cc\u00c9\3"+
		"\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf"+
		"\17\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1\u00d2\7\f\2\2\u00d2\u00d3\5\4\3"+
		"\2\u00d3\u00d4\7\r\2\2\u00d4\21\3\2\2\2\u00d5\u00d6\7%\2\2\u00d6\u00d7"+
		"\7+\2\2\u00d7\23\3\2\2\2\u00d8\u00d9\7+\2\2\u00d9\u00da\7$\2\2\u00da\u00db"+
		"\5\4\3\2\u00db\25\3\2\2\2\u00dc\u00dd\t\3\2\2\u00dd\27\3\2\2\2\u00de\u00df"+
		"\t\4\2\2\u00df\31\3\2\2\2\u00e0\u00e1\t\5\2\2\u00e1\33\3\2\2\2\32\36\""+
		"*CGLR`~\u0087\u008f\u0096\u009c\u009e\u00a0\u00a6\u00aa\u00b1\u00b5\u00ba"+
		"\u00bf\u00c4\u00c9\u00ce";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}