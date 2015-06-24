package com.besuikerd.autologistics.common.lib.collection;

import java.util.*;

/**
 * Stack implementation with constant time pushAll operation (when the stack being pushed is an instance of LinkedStack,
 * else O(n) )
 * @param <A>
 */
public class LinkedStack<A> extends AbstractList<A> implements Stack<A>{

    private int size;
    protected Node first;
    protected Node last;

    public LinkedStack(){
    }

    public LinkedStack(Collection<A> col){
        addAll(col);
    }

    @Override
    public A get(int index) {
        return getNode(index).item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public A pop() {
        checkSize(1);
        Node popped = first;
        first = popped.next;

        if(first == null){
            last = null;
        } else{
            first.previous = null;
        }
        size--;
        if(size != 0 && first == null){
            throw new IllegalStateException("" + size + last);
        }
        return popped.item;
    }

    @Override
    public Stack<A> pop(int n) {
        if(n == 0){
            return new LinkedStack<A>();
        }
        checkSize(n);
        LinkedStack<A> poppedStack = new LinkedStack<A>();
        poppedStack.size = n;
        poppedStack.first = this.first;
        Node split = getNode(n - 1);
        poppedStack.last = split;
        first = split.next;
        if(first == null){
            last = null;
        } else{
            first.previous = null;
        }
        size -= n;
        return poppedStack;
    }

    @Override
    public void add(int index, A element) {
        Node newNode = null;
        if(index == size){
            Node oldLast = last;
            newNode = new Node(oldLast, element, null);
            last = newNode;
            if(oldLast != null){
                oldLast.next = newNode;
            }
        } else{
            Node old = getNode(index);
            newNode = new Node(null, element, old);
            if(old.previous != null){
                newNode.previous = old.previous;
                old.previous.next = newNode;
            }
            old.previous = newNode;
        }
        if(index == 0){
            first = newNode;
        }
        size += 1;
    }

    @Override
    public A remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public A peek() {
        if(first == null){
            throw new NoSuchElementException("Stack.first");
        }
        return first.item;
    }

    @Override
    public Stack<A> peek(int n) {
        checkSize(n);
        Node last = getNode(n - 1);
        LinkedStack<A> peekStack = new LinkedStack<A>();
        peekStack.first = first;
        peekStack.last = last;
        peekStack.size = n;
        return peekStack;
    }

    @Override
    public void push(A value) {
        Node oldFirst = first;
        Node newNode = new Node(null, value, oldFirst);
        first = newNode;
        if(oldFirst == null){
            last = newNode;
        } else{
            oldFirst.previous = first;
        }
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void pushAll(Collection<A> values) {
        if(values.size() == 0){
            return;
        }
        if(values instanceof LinkedStack){
            LinkedStack<A> stack = (LinkedStack<A>) values;
            if(first != null){
                first.previous = stack.last;
                stack.last.next = first;
            } else{
                last = stack.last;
            }
            first = stack.first;
        } else{
            Iterator<A> iterator = values.iterator();
            Node firstElem = new Node(null, iterator.next(), null);
            Node elem = firstElem;
            while(iterator.hasNext()){
                Node nextElem = new Node(elem, iterator.next(), null);
                elem.next = nextElem;
                elem = nextElem;
            }
            elem.next = first;
            first = firstElem;
        }
        size += values.size();
    }

    protected A unlink(Node node){
        if(node.previous == null){
            first = node.next;
        } else {
            node.previous.next = node.next;
        }

        if(node.next == null){
            last = node.previous;
        } else{
            node.next.previous = node.previous;
        }
        size--;
        return node.item;
    }


    protected Node getNode(int index){
        checkIndex(index);
        Node node = null;
        if(index > size / 2){
            node = first;
            for(int i = 0 ; i < index ; i++){
                node = node.next;
            }
        } else{
            node = last;
            for (int i = size - 1; i > index; i--){
                node = node.previous;
            }
        }
        return node;
    }

    private void checkIndex(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkSize(int minSize){
        if(size < minSize){
            throw new IndexOutOfBoundsException("Index: " + (minSize - 1) + ", Size: " + size);
        }
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    protected class Node{
        Node previous;
        private A item;
        Node next;
        public Node(Node previous, A item, Node next){
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }
}
