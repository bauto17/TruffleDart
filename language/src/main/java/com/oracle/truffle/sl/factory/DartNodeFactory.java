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

    private Map<String, RootCallTarget> allFunction = new HashMap<>();
    public Map<String, RootCallTarget> getAllFunctions() {
        return allFunction;
    }

    @Override
    public void enterCompilationUnit(DartParser.CompilationUnitContext ctx) {
        System.out.println("inicio el programa");
    }

    @Override
    public void enterTopLevelDefinition(DartParser.TopLevelDefinitionContext ctx) {
        this.scope = new LexicalScope(scope);
        this.current = new DartTopLevelDefinition(this.scope, current);
    }

    @Override
    public void exitTopLevelDefinition(DartParser.TopLevelDefinitionContext ctx) {
        this.scope = this.scope.contex;
    }

    @Override
    public void enterFunctionSignature(DartParser.FunctionSignatureContext ctx) {
        this.current = new DartFunctionSignature(this.scope, current);
    }

    @Override
    public void exitFunctionSignature(DartParser.FunctionSignatureContext ctx) {
    }

    @Override public void enterFunctionBody(DartParser.FunctionBodyContext ctx) {
        this.scope = new LexicalScope(scope);
        this.current = new DartFunctionBody(this.scope, current);
    }

    @Override public void exitFunctionBody(DartParser.FunctionBodyContext ctx) {
        this.scope = this.scope.contex;
    }

    @Override
    public void enterIdentifier(DartParser.IdentifierContext ctx) {
        current.addIdentifier(ctx.start.getText());
    }

    /*@Override
    public void enterFunctionSignature(DartParser.FunctionSignatureContext ctx) {

        current = new DartFunction(this.scope, current);
    }

    @Override
    public void exitFunctionSignature(DartParser.FunctionSignatureContext ctx) {

    }*/



    @Override
    public void enterBlock(DartParser.BlockContext ctx) {
        this.scope = new LexicalScope(scope);
    }

    @Override
    public void exitBlock(DartParser.BlockContext ctx) {
        scope = scope.contex;
    }


}
