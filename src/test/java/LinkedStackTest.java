import com.besuikerd.autologistics.common.lib.collection.LinkedStack;
import com.besuikerd.autologistics.common.lib.collection.Stack;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class LinkedStackTest {


    @Test
    public void testPushElement(){
        Stack<Object> stack = new LinkedStack<Object>();
        assertEquals(stack.size(), 0);
        Object elem = new Object();
        stack.push(elem);
        assertEquals(stack.size(), 1);
        assertEquals(stack.peek(), elem);

        Object poppedElem = stack.pop();
        assertEquals(elem, poppedElem);
    }

    @Test
    public void testPushAll(){
        Stack<Object> stack = new LinkedStack<Object>();
        Stack<Object> toPush = new LinkedStack<Object>();

        Object a = "a";
        Object b = "b";
        Object c = "c";

        stack.push(a);
        toPush.push(b);
        toPush.push(c);

        stack.pushAll(toPush);

        Stack<Object> peek = stack.peek(3);
        assertEquals(stack.get(0), c);
        assertEquals(stack.get(1), b);
        assertEquals(stack.get(2), a);
    }

    @Test
    public void testPop(){
        Stack<Integer> stack = new LinkedStack<Integer>();
        int length = 10;
        for(int i = 0 ; i < length ; i++){
            stack.push(i);
        }
        for(int i = length - 1; i >= 0 ; i--){
            assertEquals(i, (int) stack.pop());
        }
        assertEquals(stack.size(), 0);
    }

    @Test
    @Ignore
    public void testPerformance(){
        Stack<Integer> stack = new LinkedStack<Integer>();
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        for(int i = 0 ; i < 100; i++){
            stack.push(i);
            linkedList.addFirst(i);
        }
        long time = System.currentTimeMillis();
        stack.pushAll(stack);
        System.out.println("stack time: " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        linkedList.addAll(0, linkedList);
        System.out.println("linkedList time: " + (System.currentTimeMillis() - time));

        System.out.println(stack);
        System.out.println(linkedList);
        Iterator<Integer> stackIt = stack.iterator();
        Iterator<Integer> linkIt = stack.iterator();
        while(stackIt.hasNext() && linkIt.hasNext()){
            assertEquals(stackIt.next(), linkIt.next());
        }
    }

    @Test
    public void testAdd(){
        Stack<Integer> stack = new LinkedStack<Integer>();
        int length = 10;
        for(int i = 0 ; i < length ; i++){
            stack.add(i);
        }

        for(int i = 0 ; i < length ; i++){
            assertEquals(i, (int) stack.get(i));
        }
    }

    @Test
    public void testAddHead(){
        Stack<Integer> withPush = new LinkedStack<Integer>();
        Stack<Integer> withAdd = new LinkedStack<Integer>();
        int length = 10;
        for(int i = 0 ; i < length ; i++){
            withPush.push(i);
            withAdd.add(0, i);
        }

        for(int i = 0 ; i < length ; i++){
            int withPushValue = withPush.get(i);
            int withAddValue = withAdd.get(i);
            assertEquals(withPushValue, withAddValue);
        }
    }

    @Test
    public void testCombined() {
        Stack<Integer> stack = new LinkedStack<Integer>();
        stack.push(5);
        Stack<Integer> toAdd = new LinkedStack<Integer>();
        toAdd.push(1);
        stack.pushAll(toAdd);
        stack.add(3);
        stack.pop(1);
        stack.remove(0);
        stack.addAll(Arrays.asList(1,2,3,4));
    }

    @Test
    public void testAddNothing(){
        Stack<Integer> stack = new LinkedStack<Integer>();
        stack.addAll(Collections.<Integer>emptyList());
        stack.push(0);
        stack.addAll(Collections.<Integer>emptyList());
        assertEquals(stack.size(), 1);
        stack.pop();
        stack.addAll(Collections.<Integer>emptyList());
        assertEquals(stack.size(), 0);
        stack.add(1);
        stack.addAll(Collections.<Integer>emptyList());
        assertEquals(stack.size(), 1);
        stack.pop(1);
        stack.addAll(Collections.<Integer>emptyList());
        assertEquals(stack.size(), 0);
    }

    @Test
    public void testRemove(){
        Stack<Integer> stack = new LinkedStack<Integer>();
        for(int i = 0 ; i < 10 ; i++){
            stack.add(i);

        }

        for(int i = 0 ; i < 5 ; i++){
            stack.remove(i);
        }

        for(int i = 0 ; i < 5 ; i++){
            assertEquals(i * 2 + 1, (int) stack.get(i));
        }
    }

    @Test
    public void testModifiedPushAll(){
        Stack<Integer> stack = new LinkedStack<Integer>();
        LinkedList<Integer> toPush = new LinkedList<Integer>();

        for(int i = 0 ; i < 5; i++){
            toPush.push(i);
        }
        System.out.println(toPush);

        stack.pushAll(toPush);
        System.out.println(stack);
        stack.pop();
        stack.pushAll(toPush);
//        assertEquals(stack.size(), 6);
        System.out.println(stack);
        System.out.println(toPush);
    }
}
