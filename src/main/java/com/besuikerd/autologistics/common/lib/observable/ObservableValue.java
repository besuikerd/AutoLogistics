package com.besuikerd.autologistics.common.lib.observable;

public interface ObservableValue<A> {
    void addObserver(ValueObserver<A> observer);
    boolean removeObserver(ValueObserver<A> observer);

    A getValue();
    void setValue(A value);
}
