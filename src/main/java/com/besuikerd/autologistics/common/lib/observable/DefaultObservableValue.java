package com.besuikerd.autologistics.common.lib.observable;

import java.util.ArrayList;
import java.util.List;

public class DefaultObservableValue<A> implements ObservableValue<A>{

    private List<ValueObserver<A>> observers;
    protected A value;

    public DefaultObservableValue(A value) {
        this.value = value;
        this.observers = new ArrayList<ValueObserver<A>>();
    }

    @Override
    public void addObserver(ValueObserver<A> observer) {
        observers.add(observer);
    }

    @Override
    public boolean removeObserver(ValueObserver<A> observer) {
        return observers.remove(observer);
    }

    @Override
    public A getValue() {
        return value;
    }

    @Override
    public void setValue(A value) {
        A oldValue = this.value;
        this.value = value;
        notifyObservers(oldValue);
    }

    protected void notifyObservers(A oldValue){
        for(ValueObserver<A> observer : observers){
            observer.onValueChanged(oldValue, value);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}