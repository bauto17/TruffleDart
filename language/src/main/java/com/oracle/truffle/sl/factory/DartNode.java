package com.oracle.truffle.sl.factory;

import com.oracle.truffle.sl.errors.DartParseError;

abstract class DartNode {
    public DartNode parent = null;
    public DartNodeFactory.LexicalScope scope = null;

    public DartNode(DartNodeFactory.LexicalScope scope, DartNode parent) {
        this.scope = scope;
        this.parent = parent;
    }

    public void addFunctionBody() {
        throw new RuntimeException("syntax no supported");
    }

    public void addIdentifier(String identifier) {
        throw new RuntimeException("syntax no supported");
    }
}
