package com.besuikerd.autologistics.common.lib.observable;

public interface ValueObserver<A> {
    void onValueChanged(A oldValue, A newValue);
}