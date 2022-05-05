package com.oracle.truffle.sl.factory;

import com.oracle.truffle.sl.errors.DartParseError;

abstract class DartNode {
    public void addIdentifier(String identifier) {
        throw new RuntimeException("syntax no supported");
    }
}
