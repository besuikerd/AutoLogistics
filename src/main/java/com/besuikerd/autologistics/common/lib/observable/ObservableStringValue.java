package com.besuikerd.autologistics.common.lib.observable;

public class ObservableStringValue extends DefaultObservableValue<String>{

    public ObservableStringValue(String value) {
        super(value);
    }

    public ObservableStringValue(){
        this("");
    }
}
