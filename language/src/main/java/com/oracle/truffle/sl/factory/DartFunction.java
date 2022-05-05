package com.oracle.truffle.sl.factory;

public class DartFunction extends DartNode {
    public String name = null;

    @Override
    public void addIdentifier(String identifier) {
        name = identifier;
    }
}
