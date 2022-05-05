package com.oracle.truffle.sl.factory;

public class DartFunction extends DartNode {
    public String name = null;

    public DartFunction(DartNodeFactory.LexicalScope scope, DartNode parent) {
        super(scope, parent);
    }


    @Override
    public void addIdentifier(String identifier) {
        name = identifier;
    }
}
