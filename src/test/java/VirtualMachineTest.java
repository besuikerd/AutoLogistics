import com.besuikerd.autologistics.common.lib.dsl.AutoLogisticsParser;
import com.besuikerd.autologistics.common.lib.dsl.AutoLogisticsParser$;
import com.besuikerd.autologistics.common.lib.dsl.Statement;
import com.besuikerd.autologistics.common.lib.dsl.vm.OptimizedVirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.NativeFunctionKeys;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.NativeFunctionLength;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.NativeFunctionType;
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.*;
import com.besuikerd.autologistics.common.lib.dsl.vm.CodeGenerator;
import com.besuikerd.autologistics.common.lib.dsl.vm.DefaultVirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.NativeFunction;
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine;
import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.junit.*;
import org.junit.runner.RunWith;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class VirtualMachineTest {
    private VirtualMachine vm;

    public VirtualMachineTest(VirtualMachine vm){
        this.vm = vm;
    }

    @Parameters
    public static Iterable<Object[]> data(){
        return Arrays.<Object[]>asList(
            new Object[]{new DefaultVirtualMachine()}
        );
    }

    @Before
    public void reset(){
        vm.load(new ArrayList<Instruction>());

        vm.addNative("println", new NativeFunction(){
            @Override
            public StackValue call(VirtualMachine vm, List<StackValue> args) {
                StackValue first = args.iterator().next();
                System.out.println(first.stringRepresentation());
                return NilValue.instance;
            }
        });

        vm.addNative("assert", new NativeFunction() {
            @Override
            public StackValue call(VirtualMachine vm, List<StackValue> args) {
                assertEquals(args.size(), 1);
                assertEquals(BooleanValue.trueValue, args.get(0));
                return NilValue.instance;
            }
        });

        vm.addNative("assertEquals", new NativeFunction() {
            @Override
            public StackValue call(VirtualMachine vm, List<StackValue> args) {
                assertEquals(args.size(), 2);
                assertEquals(args.get(0), args.get(1));
                return NilValue.instance;
            }
        });

        vm.addNative("length", NativeFunctionLength.instance);
        vm.addNative("keys", NativeFunctionKeys.instance);
        vm.addNative("type", NativeFunctionType.instance);

    }

    public List<Instruction> load(String program){
        AutoLogisticsParser$.ParseResult<scala.collection.immutable.List<Statement>> parseResult = AutoLogisticsParser.parse(AutoLogisticsParser.parser(), program);
        if(parseResult instanceof AutoLogisticsParser$.Success){
            return CodeGenerator.generate(parseResult.get());
        } else{
            AutoLogisticsParser$.ParseResult untyped = parseResult;
            AutoLogisticsParser$.NoSuccess failure = (AutoLogisticsParser$.NoSuccess) untyped;
            fail(failure.msg());
            return null;
        }
    }

    public void run(String program){
        List<Instruction> instructions = load(program);
//        for(Instruction i : instructions){
//            System.out.println(i);
//        }
        vm.load(instructions);
        long time = System.currentTimeMillis();
        vm.run(10000);
//        System.out.println(System.currentTimeMillis() - time);
        assertTermination();
    }

    public void assertTermination(){
        assertTrue("Virtual machine did not terminate", vm.isTerminated());
        if(vm.isErrorState()){
            fail(vm.getErrorMessage());
        }
    }

    @Test
    public void testBinaryExpression(){
        run("assertEquals(2 + 3, 5)");
        run("assertEquals(2 * 3, 6)");
        run("assertEquals(2 + 3 * 4, 14)");
        run("assertEquals(8 - 4 - 2, 2)");
        run("assertEquals(8 - (4 - 2), 6)");
    }

    @Test
    public void testAssignment(){
        run("x = 2 y = 3 assertEquals(x + 1, y)");
    }

    @Test
    public void testLambda(){
        run("inc = \\x -> x + 1 x = 45 assertEquals(x + 1, inc(x))");
        run("f = \\a b -> a g = \\a b -> b a = 5 b = 6 assertEquals(f(a,b), a) assertEquals(g(a,b), b)");
    }

    @Test
    public void testIfElse(){
        run("x = if(1 > 2) 3 else 2 assertEquals(x, 2)");
        run("assertEquals(if(true) 5, 5)");
        run("assertEquals(if(false) 5, null)");
    }

    @Test
    public void testBlock(){
        run("x = {null} assertEquals(x, null)");
        run("x = {assert(true) 2 + 2} assertEquals(x, 4)");
        run("assertEquals({x = 5;}, null)");
    }

//    @Ignore
    @Test
    public void testRecursion(){
        run("fib = \\x -> if(x == 0) 1 else x * fib(x - 1) assertEquals(fib(5), 120)");
//        run("f = \\x -> if(x == 0) 0 else f(x - 1) assertEquals(f(1), 0)");
//        run("f = \\x -> f(x + 1) f(0)");
    }

    @Test
    public void testWhile(){
        run("i = 0 while(i < 10){i = i + 1} assertEquals(i, 10)");
    }

    @Test
    public void testList(){
        run(
            "list = [1,2,3] assertEquals(length(list), 3) assertEquals(list[0], 1)" +
            "list[42] = true assertEquals(list[41], null) assertEquals(list[42], true)"
        );
    }

    @Test
    public void testObjects(){
        run(
            "obj = {name = \"John\" surname = \"Doe\" age = 25} k = keys(obj) assertEquals(length(k), 3)"
        );
    }

    @Test
    public void testHigherOrder(){
        run(
            "foreach = \\list f -> {l = length(list) i = 0 while(i < l) {f(list[i]) i = i + 1}}"
            + "list = [1,2,3,4]" +
            "foreach(list, \\e -> assertEquals(e, list[e - 1]))"
        );
    }

    @Test
    public void testType(){
        run("assertEquals(type(5), \"int\")");
        run("assertEquals(type(5.0), \"double\")");
        run("assertEquals(type(\"hoi\"), \"string\")");
        run("assertEquals(type(true), \"boolean\")");
        run("assertEquals(type(false), \"boolean\")");
        run("assertEquals(type(null), \"null\")");
        run("assertEquals(type([1,2,3]), \"list\")");
        run("assertEquals(type({a = 5 b = 6}), \"object\")");
        run("assertEquals(type(\\x -> x + 1), \"closure\")");
        run("assertEquals(type(type), \"native\")");
    }

    @Test
    public void testSerialization() throws IOException {
        vm.load(load("x = 4 y = x + 2 assertEquals(y, x + 2)"));
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        vm.serialize(output);
        DataInput input = ByteStreams.newDataInput(output.toByteArray());
        vm.deserialize(input);
        vm.run(100000);
        assertTermination();
    }
}
