package com.besuikerd.autologistics.common.lib.dsl.vm.codegen;

import com.besuikerd.autologistics.common.lib.antlr.AutoLogisticsBaseVisitor;
import com.besuikerd.autologistics.common.lib.antlr.AutoLogisticsParser;
import com.besuikerd.autologistics.common.lib.antlr.AutoLogisticsParser.*;

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.compare.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.AndInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.logical.OrInstruction;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.numeric.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.google.common.collect.Lists;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeGeneratorVisitor extends AutoLogisticsBaseVisitor<List<Instruction>>{

    private List<Instruction> instructions;

    private CodeGeneratorVisitor(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    private CodeGeneratorVisitor() {
        this(new ArrayList<Instruction>());
    }

    /**
     * @throws NumberFormatException
     */
    public static List<Instruction> visit(ProgramContext ctx) {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        return ctx.accept(visitor);
    }

    //Assignments

    @Override
    public List<Instruction> visitProgram(ProgramContext ctx) {
        instructions.add(OpenScopeInstruction.instance);
        for(ExpContext e : ctx.exp()){
            e.accept(this);
            instructions.add(PopInstruction.instance);
        }
        instructions.add(CloseScopeInstruction.instance);
        return instructions;
    }

    @Override
    public List<Instruction> visitAssignExp(AssignExpContext ctx) {
        ctx.exp().accept(this);
        instructions.add(DupInstruction.instance);
        instructions.add(new PutInstruction(ctx.Identifier().getText()));
        return instructions;
    }

    @Override
    public List<Instruction> visitAssignLocalExp(AssignLocalExpContext ctx) {
        ctx.exp().accept(this);
        instructions.add(DupInstruction.instance);
        instructions.add(new PutInstruction(ctx.Identifier().getText(), true));
        return instructions;
    }

    @Override
    public List<Instruction> visitAssignIndexExp(AssignIndexExpContext ctx) {
        ctx.exp(1).accept(this);
        instructions.add(DupInstruction.instance);
        int indexSize = ctx.index().size();

        ctx.exp(0).accept(this);

        for(int i = 0 ; i < indexSize - 1 ; i++){
            ctx.index(i).accept(this);
            instructions.add(GetFieldInstruction.instance);
        }

        ctx.index(indexSize - 1).accept(this);

        instructions.add(PutFieldInstruction.instance);
        return instructions;
    }

    @Override
    public List<Instruction> visitIndexExp(IndexExpContext ctx) {
        ctx.referrable().accept(this);
        for(IndexContext index : ctx.index()){
            index.accept(this);
            instructions.add(GetFieldInstruction.instance);
        }
        return instructions;
    }

    @Override
    public List<Instruction> visitIndex(IndexContext ctx) {
        ctx.exp().accept(this);
        return instructions;
    }

    @Override
    public List<Instruction> visitAssignFieldExp(AssignFieldExpContext ctx) {
        ctx.exp(1).accept(this);
        instructions.add(DupInstruction.instance);

        int fieldSize = ctx.field().size();

        for(int i = 0 ; i < fieldSize - 1 ; i++){
            ctx.field(i).accept(this);
            instructions.add(GetFieldInstruction.instance);
        }

        ctx.exp(0).accept(this);
        ctx.field(fieldSize - 1).accept(this);
        instructions.add(PutFieldInstruction.instance);
        return instructions;
    }

    @Override
    public List<Instruction> visitFieldExp(FieldExpContext ctx) {
        ctx.referrable().accept(this);
        for(FieldContext field : ctx.field()){
            field.accept(this);
            instructions.add(GetFieldInstruction.instance);
        }
        return instructions;
    }

    @Override
    public List<Instruction> visitField(FieldContext ctx) {
        instructions.add(new PushInstruction(new StringValue(ctx.Identifier().getText())));
        return instructions;
    }

    //Binary Expressions

    @Override
    public List<Instruction> visitAddSubExp(AddSubExpContext ctx) {
        for(ExpContext exp : ctx.exp()){
            exp.accept(this);
        }

        OpAddSubContext op = ctx.opAddSub();
        if(op.ADD() != null){
            instructions.add(AddInstruction.instance);
        } else if(op.SUB() != null){
            instructions.add(SubInstruction.instance);
        }
        return instructions;
    }

    @Override
    public List<Instruction> visitMulDivModExp(MulDivModExpContext ctx) {
        for(ExpContext exp : ctx.exp()){
            exp.accept(this);
        }

        OpMulDivModContext op = ctx.opMulDivMod();
        if(op.MUL() != null){
            instructions.add(MulInstruction.instance);
        } else if(op.DIV() != null){
            instructions.add(DivInstruction.instance);
        } else if(op.MOD() != null){
            instructions.add(ModInstruction.instance);
        }
        return instructions;
    }

    @Override
    public List<Instruction> visitCompareExp(CompareExpContext ctx) {
        for(ExpContext exp : ctx.exp()){
            exp.accept(this);
        }

        OpCompareContext op = ctx.opCompare();
        if(op.GT() != null){
            instructions.add(GTInstruction.instance);
        } else if(op.GTE() != null){
            instructions.add(GTEInstruction.instance);
        } else if(op.LT() != null){
            instructions.add(LTInstruction.instance);
        } else if(op.LTE() != null){
            instructions.add(LTEInstruction.instance);
        } else if(op.EQ() != null){
            instructions.add(EQInstruction.instance);
        } else if(op.NEQ() != null){
            instructions.add(NEQInstruction.instance);
        }
        return instructions;
    }

    @Override
    public List<Instruction> visitAndExp(AndExpContext ctx) {
        instructions.add(AndInstruction.instance);
        return instructions;
    }

    @Override
    public List<Instruction> visitOrExp(OrExpContext ctx) {
        instructions.add(OrInstruction.instance);
        return instructions;
    }

    //Unary Expressions

    @Override
    public List<Instruction> visitNegateExp(NegateExpContext ctx) {
        instructions.add(new GetInstruction("not"));
        ctx.exp().accept(this);
        instructions.add(new CallInstruction(1));
        return instructions;
    }

    //Constants

    @Override
    public List<Instruction> visitStringExp(StringExpContext ctx) {
        String literal = ctx.StringLiteral().getText();
        StringValue value = new StringValue(literal.substring(1, literal.length() - 1));
        instructions.add(new PushInstruction(value));
        return instructions;
    }

    @Override
    public List<Instruction> visitNullExp(NullExpContext ctx) {
        instructions.add(PushInstruction.pushNil);
        return instructions;
    }

    @Override
    public List<Instruction> visitDecimalExp(DecimalExpContext ctx) {
        double value = Double.parseDouble(ctx.Decimal().getText());
        instructions.add(new PushInstruction(new DecimalValue(value)));
        return instructions;
    }

    @Override
    public List<Instruction> visitIntegerExp(IntegerExpContext ctx) {
        int value = Integer.parseInt(ctx.Integer().getText());
        instructions.add(new PushInstruction(new IntegerValue(value)));
        return instructions;
    }

    @Override
    public List<Instruction> visitBooleanExp(BooleanExpContext ctx) {
        BooleanValue value = ctx.TRUE() != null ? BooleanValue.trueValue : BooleanValue.falseValue;
        instructions.add(new PushInstruction(value));
        return instructions;
    }

    @Override
    public List<Instruction> visitListExp(ListExpContext ctx) {
        ExpListContext expList = ctx.expList();
        if(expList != null){
            List<ExpContext> exps = ctx.expList().exp();

            for(int i = exps.size() - 1 ; i >= 0 ; i--){
                exps.get(i).accept(this);
            }
            instructions.add(new PushListInstruction(exps.size()));
        } else{
            instructions.add(new PushListInstruction(0));
        }
        return instructions;
    }

    @Override
    public List<Instruction> visitObjectExp(ObjectExpContext ctx) {
        List<String> keys = new LinkedList<String>();
        for(KvContext kv : ctx.kvList().kv()){
            keys.add(0, kv.Identifier().getText());
            kv.exp().accept(this);
        }
        instructions.add(new PushObjectInstruction(keys));
        return instructions;
    }

    // Control flow

    @Override
    public List<Instruction> visitWhileExp(WhileExpContext ctx) {
        List<Instruction> allInstructions = this.instructions;
        List<Instruction> conditionInstructions = new ArrayList<Instruction>();
        List<Instruction> bodyInstructions = new ArrayList<Instruction>();


        this.instructions = conditionInstructions;

        BlockOrExpContext condition = ctx.blockOrExp(0);
        BlockContext conditionBlock = condition.block();
        if(conditionBlock != null){
            instructions.add(OpenScopeInstruction.instance);
            List<ExpContext> exps = conditionBlock.exp();
            if(exps.isEmpty()){
                instructions.add(PushInstruction.pushNil);
            } else{
                for(ExpContext exp : exps.subList(0, exps.size() - 1)){
                    exp.accept(this);
                    instructions.add(PopInstruction.instance);
                }
                exps.get(exps.size() - 1).accept(this);
            }
        } else{
            condition.exp().accept(this);
        }

        this.instructions = bodyInstructions;

        ctx.blockOrExp(1).accept(this);

        this.instructions = allInstructions;
        instructions.addAll(conditionInstructions);



        if(conditionBlock != null){
            bodyInstructions.add(CloseScopeInstruction.instance);
        }
        bodyInstructions.add(new LoadInstruction(conditionInstructions));



        instructions.add(new RepeatedBranchInstruction(bodyInstructions));

        if(conditionBlock != null){
            instructions.add(CloseScopeInstruction.instance);
        }

        instructions.add(new PushInstruction(NilValue.instance));
        return instructions;
    }

    @Override
    public List<Instruction> visitBlock(BlockContext ctx) {
        List<ExpContext> exps = ctx.exp();
        instructions.add(OpenScopeInstruction.instance);
        if(exps.isEmpty()){
            instructions.add(PushInstruction.pushNil);
        } else{
            for(ExpContext exp : exps.subList(0, exps.size() - 1)){
                exp.accept(this);
                instructions.add(PopInstruction.instance);
            }
            exps.get(exps.size() - 1).accept(this);
        }
        instructions.add(CloseScopeInstruction.instance);
        return instructions;
    }

    @Override
    public List<Instruction> visitIfElseExp(IfElseExpContext ctx) {
        List<ExpContext> exps = ctx.exp();

        exps.get(0).accept(this);

        List<Instruction> allInstructions = this.instructions;
        List<Instruction> branchTrue = new ArrayList<Instruction>();
        List<Instruction> branchFalse = new ArrayList<Instruction>();

        this.instructions = branchTrue;
        exps.get(1).accept(this);

        if(exps.size() == 3){
            this.instructions = branchFalse;
            exps.get(2).accept(this);
        } else{
            branchFalse.add(PushInstruction.pushNil);
        }

        this.instructions = allInstructions;

        instructions.add(new BranchInstruction(branchTrue, branchFalse));
        return instructions;
    }

    @Override
    public List<Instruction> visitParenExp(ParenExpContext ctx) {
        return ctx.exp().accept(this);
    }

    @Override
    public List<Instruction> visitAppExp(AppExpContext ctx) {
        ctx.exp().accept(this);
        List<ExpContext> args = ctx.expList() != null ? ctx.expList().exp() : Collections.<ExpContext>emptyList();
        for(ExpContext arg : args){
            arg.accept(this);
        }
        instructions.add(new CallInstruction(args.size()));
        return instructions;
    }

    @Override
    public List<Instruction> visitLambdaExp(LambdaExpContext ctx) {
        String closureName = null;
        if(ctx.getParent() instanceof AssignExpContext){
            closureName = ((AssignExpContext) ctx.getParent()).Identifier().getText();
        }
        List<Instruction> allInstructions = this.instructions;

        List<Instruction> bodyInstructions = new ArrayList<Instruction>();
        this.instructions = bodyInstructions;
        ctx.exp().accept(this);

        this.instructions = allInstructions;

        List<String> bindings = new ArrayList<String>();
        IdListContext idListContext = ctx.idList();
        if(idListContext != null){
            for(TerminalNode identifier : idListContext.Identifier()){
                bindings.add(identifier.getText());
            }
        }
        List<String> freeVariables = FreeVariableInstructionVisitor.visit(bodyInstructions);
        freeVariables.removeAll(bindings);
        if(closureName != null){
            freeVariables.remove(closureName);
        }

        PushClosureInstruction instruction = new PushClosureInstruction(closureName, bindings, freeVariables, bodyInstructions);
        instructions.add(instruction);
        return instructions;
    }

    @Override
    public List<Instruction> visitVariableExp(VariableExpContext ctx) {
        instructions.add(new GetInstruction(ctx.Identifier().getText()));
        return instructions;
    }

    private static final Pattern ModIdPattern = Pattern.compile("<(\\w+):(\\w+)(?::(\\d+))?>");

    @Override
    public List<Instruction> visitItemExp(ItemExpContext ctx) {

        String name = ctx.Item().getText();
        List<String> keys;
        Matcher matcher = ModIdPattern.matcher(name);
        if(matcher.find()){
            String mod = matcher.group(1);
            String item = matcher.group(2);
            keys = Lists.newArrayList("name", "mod", "type");

            instructions.add(new PushInstruction(new StringValue("item")));
            instructions.add(new PushInstruction(new StringValue(mod)));
            instructions.add(new PushInstruction(new StringValue(item)));
            String meta = matcher.group(3);
            if(meta != null){
                keys.add(0, "meta");
                instructions.add(new PushInstruction(new IntegerValue(Integer.parseInt(meta))));
            }


        } else{
            name = name.substring(1, name.length() - 1);
            keys = Arrays.asList("name", "type");
            instructions.add(new PushInstruction(new StringValue("name")));
            instructions.add(new PushInstruction(new StringValue(name)));

        }
        instructions.add(new PushObjectInstruction(keys));


        return instructions;
    }

    @Override
    public List<Instruction> visitCoordExp(CoordExpContext ctx) {
        List<String> keys = Arrays.asList("z", "y", "x", "type");
        String type = ctx.TILDE() == null ? "absolute" : "relative";

        instructions.add(new PushInstruction(new StringValue(type)));
        for(TerminalNode coord : ctx.Integer()){
            instructions.add(new PushInstruction(new IntegerValue(Integer.parseInt(coord.getText()))));
        }
        instructions.add(new PushObjectInstruction(keys));

        return instructions;
    }

    @Override
    public List<Instruction> visitItemFilter(ItemFilterContext ctx) {
        instructions.add(new GetInstruction("_filter"));
        for(ExpContext exp : ctx.exp()){
            exp.accept(this);
        }
        instructions.add(new CallInstruction(2));
        return instructions;
    }

    @Override
    public List<Instruction> visitTransferExp(TransferExpContext ctx) {
        instructions.add(new GetInstruction("_transfer"));
        for(ExpContext exp: ctx.exp()){
            exp.accept(this);
        }
        instructions.add(new CallInstruction(2));
        return instructions;
    }
}
