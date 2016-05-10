// Generated from /home/nick/projects/minecraft/AutoLogistics/src/main/antlr/AutoLogistics.g4 by ANTLR 4.5

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
		RULE_program = 0, RULE_exp = 1, RULE_block = 2, RULE_blockOrExp = 3, RULE_referrable = 4, 
		RULE_expList = 5, RULE_idList = 6, RULE_kvList = 7, RULE_index = 8, RULE_field = 9, 
		RULE_kv = 10, RULE_opMulDivMod = 11, RULE_opAddSub = 12, RULE_opCompare = 13;
	public static final String[] ruleNames = {
		"program", "exp", "block", "blockOrExp", "referrable", "expList", "idList", 
		"kvList", "index", "field", "kv", "opMulDivMod", "opAddSub", "opCompare"
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
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
				{
				{
				setState(28);
				exp(0);
				setState(30);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(29);
					match(SEMICOLON);
					}
				}

				}
				}
				setState(36);
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
	public static class ReferrableExpContext extends ExpContext {
		public ReferrableContext referrable() {
			return getRuleContext(ReferrableContext.class,0);
		}
		public ReferrableExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).enterReferrableExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AutoLogisticsListener ) ((AutoLogisticsListener)listener).exitReferrableExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AutoLogisticsVisitor ) return ((AutoLogisticsVisitor<? extends T>)visitor).visitReferrableExp(this);
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
			setState(92);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				_localctx = new NegateExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(38);
				match(NOT);
				setState(39);
				exp(17);
				}
				break;
			case 2:
				{
				_localctx = new LambdaExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(40);
				match(BACKSLASH);
				setState(42);
				_la = _input.LA(1);
				if (_la==Identifier) {
					{
					setState(41);
					idList();
					}
				}

				setState(44);
				match(ARROWRIGHT);
				setState(45);
				exp(11);
				}
				break;
			case 3:
				{
				_localctx = new AssignExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(46);
				match(Identifier);
				setState(47);
				match(BECOMES);
				setState(48);
				exp(6);
				}
				break;
			case 4:
				{
				_localctx = new AssignLocalExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(49);
				match(LOCAL);
				setState(50);
				match(Identifier);
				setState(51);
				match(BECOMES);
				setState(52);
				exp(5);
				}
				break;
			case 5:
				{
				_localctx = new IntegerExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(53);
				match(Integer);
				}
				break;
			case 6:
				{
				_localctx = new DecimalExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(54);
				match(Decimal);
				}
				break;
			case 7:
				{
				_localctx = new BooleanExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(55);
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
				setState(56);
				match(StringLiteral);
				}
				break;
			case 9:
				{
				_localctx = new NullExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(57);
				match(NULL);
				}
				break;
			case 10:
				{
				_localctx = new IfElseExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(58);
				match(IF);
				setState(59);
				exp(0);
				setState(60);
				exp(0);
				setState(63);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(61);
					match(ELSE);
					setState(62);
					exp(0);
					}
					break;
				}
				}
				break;
			case 11:
				{
				_localctx = new ListExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(65);
				match(LBRACKET);
				setState(67);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
					{
					setState(66);
					expList();
					}
				}

				setState(69);
				match(RBRACKET);
				}
				break;
			case 12:
				{
				_localctx = new ObjectExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(70);
				match(LCURLY);
				setState(72);
				_la = _input.LA(1);
				if (_la==Identifier) {
					{
					setState(71);
					kvList();
					}
				}

				setState(74);
				match(RCURLY);
				}
				break;
			case 13:
				{
				_localctx = new BlockExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(75);
				block();
				}
				break;
			case 14:
				{
				_localctx = new ItemExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(76);
				match(Item);
				}
				break;
			case 15:
				{
				_localctx = new CoordExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(78);
				_la = _input.LA(1);
				if (_la==TILDE) {
					{
					setState(77);
					match(TILDE);
					}
				}

				setState(80);
				match(LPAREN);
				setState(81);
				match(Integer);
				setState(82);
				match(COMMA);
				setState(83);
				match(Integer);
				setState(84);
				match(COMMA);
				setState(85);
				match(Integer);
				setState(86);
				match(RPAREN);
				}
				break;
			case 16:
				{
				_localctx = new WhileExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(87);
				match(WHILE);
				setState(88);
				blockOrExp();
				setState(89);
				blockOrExp();
				}
				break;
			case 17:
				{
				_localctx = new ReferrableExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(91);
				referrable(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(144);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(142);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new MulDivModExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(94);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(95);
						opMulDivMod();
						setState(96);
						exp(17);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(98);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(99);
						opAddSub();
						setState(100);
						exp(16);
						}
						break;
					case 3:
						{
						_localctx = new CompareExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(102);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(103);
						opCompare();
						setState(104);
						exp(15);
						}
						break;
					case 4:
						{
						_localctx = new AndExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(106);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(107);
						match(AND);
						setState(108);
						exp(14);
						}
						break;
					case 5:
						{
						_localctx = new OrExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(109);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(110);
						match(OR);
						setState(111);
						exp(13);
						}
						break;
					case 6:
						{
						_localctx = new ItemFilterContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(112);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(113);
						match(AT);
						setState(114);
						exp(9);
						}
						break;
					case 7:
						{
						_localctx = new TransferExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(115);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(116);
						match(TRANSFER);
						setState(117);
						exp(8);
						}
						break;
					case 8:
						{
						_localctx = new AssignIndexExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(118);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(120); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(119);
							index();
							}
							}
							setState(122); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==LBRACKET );
						setState(124);
						match(BECOMES);
						setState(125);
						exp(4);
						}
						break;
					case 9:
						{
						_localctx = new AssignFieldExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(127);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(129); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(128);
							field();
							}
							}
							setState(131); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==DOT );
						setState(133);
						match(BECOMES);
						setState(134);
						exp(3);
						}
						break;
					case 10:
						{
						_localctx = new AppExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(136);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(137);
						match(LPAREN);
						setState(139);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
							{
							setState(138);
							expList();
							}
						}

						setState(141);
						match(RPAREN);
						}
						break;
					}
					} 
				}
				setState(146);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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
			setState(147);
			match(LCURLY);
			setState(154);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
				{
				{
				setState(148);
				exp(0);
				setState(150);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(149);
					match(SEMICOLON);
					}
				}

				}
				}
				setState(156);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(157);
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
			setState(161);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(159);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
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

	public static class ReferrableContext extends ParserRuleContext {
		public ReferrableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_referrable; }
	 
		public ReferrableContext() { }
		public void copyFrom(ReferrableContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VariableExpContext extends ReferrableContext {
		public TerminalNode Identifier() { return getToken(AutoLogisticsParser.Identifier, 0); }
		public VariableExpContext(ReferrableContext ctx) { copyFrom(ctx); }
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
	public static class IndexExpContext extends ReferrableContext {
		public ReferrableContext referrable() {
			return getRuleContext(ReferrableContext.class,0);
		}
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public IndexExpContext(ReferrableContext ctx) { copyFrom(ctx); }
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
	public static class ParenExpContext extends ReferrableContext {
		public TerminalNode LPAREN() { return getToken(AutoLogisticsParser.LPAREN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(AutoLogisticsParser.RPAREN, 0); }
		public ParenExpContext(ReferrableContext ctx) { copyFrom(ctx); }
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
	public static class FieldExpContext extends ReferrableContext {
		public ReferrableContext referrable() {
			return getRuleContext(ReferrableContext.class,0);
		}
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public FieldExpContext(ReferrableContext ctx) { copyFrom(ctx); }
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

	public final ReferrableContext referrable() throws RecognitionException {
		return referrable(0);
	}

	private ReferrableContext referrable(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ReferrableContext _localctx = new ReferrableContext(_ctx, _parentState);
		ReferrableContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_referrable, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				_localctx = new ParenExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(164);
				match(LPAREN);
				setState(165);
				exp(0);
				setState(166);
				match(RPAREN);
				}
				break;
			case Identifier:
				{
				_localctx = new VariableExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(168);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(185);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(183);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new IndexExpContext(new ReferrableContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_referrable);
						setState(171);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(173); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(172);
								index();
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(175); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case 2:
						{
						_localctx = new FieldExpContext(new ReferrableContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_referrable);
						setState(177);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(179); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(178);
								field();
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(181); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					}
					} 
				}
				setState(187);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
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
		enterRule(_localctx, 10, RULE_expList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			exp(0);
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IF) | (1L << WHILE) | (1L << LOCAL) | (1L << LPAREN) | (1L << LBRACKET) | (1L << LCURLY) | (1L << COMMA) | (1L << NOT) | (1L << TILDE) | (1L << BACKSLASH) | (1L << Integer) | (1L << Decimal) | (1L << Identifier) | (1L << Item) | (1L << StringLiteral))) != 0)) {
				{
				{
				setState(190);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(189);
					match(COMMA);
					}
				}

				setState(192);
				exp(0);
				}
				}
				setState(197);
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
		enterRule(_localctx, 12, RULE_idList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(Identifier);
			setState(205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA || _la==Identifier) {
				{
				{
				setState(200);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(199);
					match(COMMA);
					}
				}

				setState(202);
				match(Identifier);
				}
				}
				setState(207);
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
		enterRule(_localctx, 14, RULE_kvList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			kv();
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA || _la==Identifier) {
				{
				{
				setState(210);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(209);
					match(COMMA);
					}
				}

				setState(212);
				kv();
				}
				}
				setState(217);
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
		enterRule(_localctx, 16, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(LBRACKET);
			setState(219);
			exp(0);
			setState(220);
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
		enterRule(_localctx, 18, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(DOT);
			setState(223);
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
		enterRule(_localctx, 20, RULE_kv);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			match(Identifier);
			setState(226);
			match(BECOMES);
			setState(227);
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
		enterRule(_localctx, 22, RULE_opMulDivMod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
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
		enterRule(_localctx, 24, RULE_opAddSub);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
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
		enterRule(_localctx, 26, RULE_opCompare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
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
		case 4:
			return referrable_sempred((ReferrableContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 16);
		case 1:
			return precpred(_ctx, 15);
		case 2:
			return precpred(_ctx, 14);
		case 3:
			return precpred(_ctx, 13);
		case 4:
			return precpred(_ctx, 12);
		case 5:
			return precpred(_ctx, 8);
		case 6:
			return precpred(_ctx, 7);
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 2);
		case 9:
			return precpred(_ctx, 22);
		}
		return true;
	}
	private boolean referrable_sempred(ReferrableContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 2);
		case 11:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\60\u00ee\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\5\2!\n\2\7\2#\n\2\f"+
		"\2\16\2&\13\2\3\3\3\3\3\3\3\3\3\3\5\3-\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3B\n\3\3\3\3\3\5\3"+
		"F\n\3\3\3\3\3\3\3\5\3K\n\3\3\3\3\3\3\3\3\3\5\3Q\n\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3_\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\6\3{\n\3\r\3\16\3|\3\3\3\3\3\3\3\3\3\3\6\3\u0084\n\3\r\3\16\3\u0085"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u008e\n\3\3\3\7\3\u0091\n\3\f\3\16\3\u0094"+
		"\13\3\3\4\3\4\3\4\5\4\u0099\n\4\7\4\u009b\n\4\f\4\16\4\u009e\13\4\3\4"+
		"\3\4\3\5\3\5\5\5\u00a4\n\5\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u00ac\n\6\3\6\3"+
		"\6\6\6\u00b0\n\6\r\6\16\6\u00b1\3\6\3\6\6\6\u00b6\n\6\r\6\16\6\u00b7\7"+
		"\6\u00ba\n\6\f\6\16\6\u00bd\13\6\3\7\3\7\5\7\u00c1\n\7\3\7\7\7\u00c4\n"+
		"\7\f\7\16\7\u00c7\13\7\3\b\3\b\5\b\u00cb\n\b\3\b\7\b\u00ce\n\b\f\b\16"+
		"\b\u00d1\13\b\3\t\3\t\5\t\u00d5\n\t\3\t\7\t\u00d8\n\t\f\t\16\t\u00db\13"+
		"\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3"+
		"\17\3\17\3\17\2\4\4\n\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\6\3\2\3"+
		"\4\3\2\25\27\3\2\23\24\3\2\30\35\u0111\2$\3\2\2\2\4^\3\2\2\2\6\u0095\3"+
		"\2\2\2\b\u00a3\3\2\2\2\n\u00ab\3\2\2\2\f\u00be\3\2\2\2\16\u00c8\3\2\2"+
		"\2\20\u00d2\3\2\2\2\22\u00dc\3\2\2\2\24\u00e0\3\2\2\2\26\u00e3\3\2\2\2"+
		"\30\u00e7\3\2\2\2\32\u00e9\3\2\2\2\34\u00eb\3\2\2\2\36 \5\4\3\2\37!\7"+
		"\21\2\2 \37\3\2\2\2 !\3\2\2\2!#\3\2\2\2\"\36\3\2\2\2#&\3\2\2\2$\"\3\2"+
		"\2\2$%\3\2\2\2%\3\3\2\2\2&$\3\2\2\2\'(\b\3\1\2()\7!\2\2)_\5\4\3\23*,\7"+
		"&\2\2+-\5\16\b\2,+\3\2\2\2,-\3\2\2\2-.\3\2\2\2./\7\'\2\2/_\5\4\3\r\60"+
		"\61\7+\2\2\61\62\7$\2\2\62_\5\4\3\b\63\64\7\t\2\2\64\65\7+\2\2\65\66\7"+
		"$\2\2\66_\5\4\3\7\67_\7)\2\28_\7*\2\29_\t\2\2\2:_\7-\2\2;_\7\5\2\2<=\7"+
		"\6\2\2=>\5\4\3\2>A\5\4\3\2?@\7\7\2\2@B\5\4\3\2A?\3\2\2\2AB\3\2\2\2B_\3"+
		"\2\2\2CE\7\f\2\2DF\5\f\7\2ED\3\2\2\2EF\3\2\2\2FG\3\2\2\2G_\7\r\2\2HJ\7"+
		"\16\2\2IK\5\20\t\2JI\3\2\2\2JK\3\2\2\2KL\3\2\2\2L_\7\17\2\2M_\5\6\4\2"+
		"N_\7,\2\2OQ\7\"\2\2PO\3\2\2\2PQ\3\2\2\2QR\3\2\2\2RS\7\n\2\2ST\7)\2\2T"+
		"U\7\22\2\2UV\7)\2\2VW\7\22\2\2WX\7)\2\2X_\7\13\2\2YZ\7\b\2\2Z[\5\b\5\2"+
		"[\\\5\b\5\2\\_\3\2\2\2]_\5\n\6\2^\'\3\2\2\2^*\3\2\2\2^\60\3\2\2\2^\63"+
		"\3\2\2\2^\67\3\2\2\2^8\3\2\2\2^9\3\2\2\2^:\3\2\2\2^;\3\2\2\2^<\3\2\2\2"+
		"^C\3\2\2\2^H\3\2\2\2^M\3\2\2\2^N\3\2\2\2^P\3\2\2\2^Y\3\2\2\2^]\3\2\2\2"+
		"_\u0092\3\2\2\2`a\f\22\2\2ab\5\30\r\2bc\5\4\3\23c\u0091\3\2\2\2de\f\21"+
		"\2\2ef\5\32\16\2fg\5\4\3\22g\u0091\3\2\2\2hi\f\20\2\2ij\5\34\17\2jk\5"+
		"\4\3\21k\u0091\3\2\2\2lm\f\17\2\2mn\7\36\2\2n\u0091\5\4\3\20op\f\16\2"+
		"\2pq\7\37\2\2q\u0091\5\4\3\17rs\f\n\2\2st\7 \2\2t\u0091\5\4\3\13uv\f\t"+
		"\2\2vw\7(\2\2w\u0091\5\4\3\nxz\f\5\2\2y{\5\22\n\2zy\3\2\2\2{|\3\2\2\2"+
		"|z\3\2\2\2|}\3\2\2\2}~\3\2\2\2~\177\7$\2\2\177\u0080\5\4\3\6\u0080\u0091"+
		"\3\2\2\2\u0081\u0083\f\4\2\2\u0082\u0084\5\24\13\2\u0083\u0082\3\2\2\2"+
		"\u0084\u0085\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0087"+
		"\3\2\2\2\u0087\u0088\7$\2\2\u0088\u0089\5\4\3\5\u0089\u0091\3\2\2\2\u008a"+
		"\u008b\f\30\2\2\u008b\u008d\7\n\2\2\u008c\u008e\5\f\7\2\u008d\u008c\3"+
		"\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0091\7\13\2\2\u0090"+
		"`\3\2\2\2\u0090d\3\2\2\2\u0090h\3\2\2\2\u0090l\3\2\2\2\u0090o\3\2\2\2"+
		"\u0090r\3\2\2\2\u0090u\3\2\2\2\u0090x\3\2\2\2\u0090\u0081\3\2\2\2\u0090"+
		"\u008a\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2"+
		"\2\2\u0093\5\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u009c\7\16\2\2\u0096\u0098"+
		"\5\4\3\2\u0097\u0099\7\21\2\2\u0098\u0097\3\2\2\2\u0098\u0099\3\2\2\2"+
		"\u0099\u009b\3\2\2\2\u009a\u0096\3\2\2\2\u009b\u009e\3\2\2\2\u009c\u009a"+
		"\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2\2\2\u009e\u009c\3\2\2\2\u009f"+
		"\u00a0\7\17\2\2\u00a0\7\3\2\2\2\u00a1\u00a4\5\6\4\2\u00a2\u00a4\5\4\3"+
		"\2\u00a3\u00a1\3\2\2\2\u00a3\u00a2\3\2\2\2\u00a4\t\3\2\2\2\u00a5\u00a6"+
		"\b\6\1\2\u00a6\u00a7\7\n\2\2\u00a7\u00a8\5\4\3\2\u00a8\u00a9\7\13\2\2"+
		"\u00a9\u00ac\3\2\2\2\u00aa\u00ac\7+\2\2\u00ab\u00a5\3\2\2\2\u00ab\u00aa"+
		"\3\2\2\2\u00ac\u00bb\3\2\2\2\u00ad\u00af\f\4\2\2\u00ae\u00b0\5\22\n\2"+
		"\u00af\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b2"+
		"\3\2\2\2\u00b2\u00ba\3\2\2\2\u00b3\u00b5\f\3\2\2\u00b4\u00b6\5\24\13\2"+
		"\u00b5\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8"+
		"\3\2\2\2\u00b8\u00ba\3\2\2\2\u00b9\u00ad\3\2\2\2\u00b9\u00b3\3\2\2\2\u00ba"+
		"\u00bd\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\13\3\2\2"+
		"\2\u00bd\u00bb\3\2\2\2\u00be\u00c5\5\4\3\2\u00bf\u00c1\7\22\2\2\u00c0"+
		"\u00bf\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4\5\4"+
		"\3\2\u00c3\u00c0\3\2\2\2\u00c4\u00c7\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c5"+
		"\u00c6\3\2\2\2\u00c6\r\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c8\u00cf\7+\2\2"+
		"\u00c9\u00cb\7\22\2\2\u00ca\u00c9\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc"+
		"\3\2\2\2\u00cc\u00ce\7+\2\2\u00cd\u00ca\3\2\2\2\u00ce\u00d1\3\2\2\2\u00cf"+
		"\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\17\3\2\2\2\u00d1\u00cf\3\2\2"+
		"\2\u00d2\u00d9\5\26\f\2\u00d3\u00d5\7\22\2\2\u00d4\u00d3\3\2\2\2\u00d4"+
		"\u00d5\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d8\5\26\f\2\u00d7\u00d4\3"+
		"\2\2\2\u00d8\u00db\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da"+
		"\21\3\2\2\2\u00db\u00d9\3\2\2\2\u00dc\u00dd\7\f\2\2\u00dd\u00de\5\4\3"+
		"\2\u00de\u00df\7\r\2\2\u00df\23\3\2\2\2\u00e0\u00e1\7%\2\2\u00e1\u00e2"+
		"\7+\2\2\u00e2\25\3\2\2\2\u00e3\u00e4\7+\2\2\u00e4\u00e5\7$\2\2\u00e5\u00e6"+
		"\5\4\3\2\u00e6\27\3\2\2\2\u00e7\u00e8\t\3\2\2\u00e8\31\3\2\2\2\u00e9\u00ea"+
		"\t\4\2\2\u00ea\33\3\2\2\2\u00eb\u00ec\t\5\2\2\u00ec\35\3\2\2\2\35 $,A"+
		"EJP^|\u0085\u008d\u0090\u0092\u0098\u009c\u00a3\u00ab\u00b1\u00b7\u00b9"+
		"\u00bb\u00c0\u00c5\u00ca\u00cf\u00d4\u00d9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}