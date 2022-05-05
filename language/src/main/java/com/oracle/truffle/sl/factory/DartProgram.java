package com.oracle.truffle.sl.factory;

import java.util.ArrayList;
import java.util.List;

public class DartProgram extends DartNode {
    public List<DartProgram> childrenList = new ArrayList<>() ;

    public DartProgram(DartNodeFactory.LexicalScope scope, DartNode parent) {
        super(scope, parent);
    }
}
