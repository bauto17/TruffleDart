package com.oracle.truffle.sl.factory;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.sl.parser.DartBaseListener;
import com.oracle.truffle.sl.parser.DartParser;

import java.util.HashMap;
import java.util.Map;

public class DartNodeFactory extends DartBaseListener {
    static class LexicalScope {
        protected final LexicalScope contex;
        protected final Map<String, DartNode> locals;

        LexicalScope(LexicalScope contex) {
            this.contex = contex;
            this.locals = new HashMap<>();
        }

        public DartNode find(String name) {
            DartNode result = locals.get(name);
            if (result != null) {
                return result;
            } else if (contex != null) {
                return contex.find(name);
            } else {
                return null;
            }
        }
    }
    private LexicalScope scope = new LexicalScope(null);

    private DartNode current = null;
    private DartBlock block = null;
    private Map<String, RootCallTarget> allFunction = new HashMap<>();

    public Map<String, RootCallTarget> getAllFunctions() {
        return allFunction;
    }

    @Override
    public void enterCompilationUnit(DartParser.CompilationUnitContext ctx) {
        System.out.println("inicio el programa");
    }

    @Override
    public void enterFunctionSignature(DartParser.FunctionSignatureContext ctx) {
        current = new DartFunction();
    }

    @Override
    public void exitFunctionSignature(DartParser.FunctionSignatureContext ctx) {
        DartFunction v = (DartFunction)current;
        scope.locals.put(v.name, v);
        current = null;
    }

    @Override
    public void enterIdentifier(DartParser.IdentifierContext ctx) {
        current.addIdentifier(ctx.start.getText());
    }

    @Override
    public void enterFunctionBody(DartParser.FunctionBodyContext ctx) {
        this.scope = new LexicalScope(scope);
    }

    @Override
    public void enterBlock(DartParser.BlockContext ctx) {
        this.scope = new LexicalScope(scope);
        block = new DartBlock();
        block.scope = scope;
    }

    @Override
    public void exitBlock(DartParser.BlockContext ctx) {
        scope = scope.contex;
    }
}
