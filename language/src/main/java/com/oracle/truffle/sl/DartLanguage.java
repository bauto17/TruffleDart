/*
 * Copyright (c) 2012, 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The Universal Permissive License (UPL), Version 1.0
 *
 * Subject to the condition set forth below, permission is hereby granted to any
 * person obtaining a copy of this software, associated documentation and/or
 * data (collectively the "Software"), free of charge and under any and all
 * copyright rights in the Software, and any and all patent rights owned or
 * freely licensable by each licensor hereunder covering either (i) the
 * unmodified Software as contributed to or provided by such licensor, or (ii)
 * the Larger Works (as defined below), to deal in both
 *
 * (a) the Software, and
 *
 * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 * one is included with the Software each a "Larger Work" to which the Software
 * is contributed by such licensors),
 *
 * without restriction, including without limitation the rights to copy, create
 * derivative works of, display, perform, and distribute the Software and make,
 * use, sell, offer for sale, import, export, have made, and have sold the
 * Software and the Larger Work(s), and to sublicense the foregoing rights on
 * either these or other terms.
 *
 * This license is subject to the following condition:
 *
 * The above copyright notice and either this complete permission notice or at a
 * minimum a reference to the UPL must be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.oracle.truffle.sl;

import com.oracle.truffle.api.*;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;
import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.sl.parser.DartParser;

import java.util.Map;


@TruffleLanguage.Registration(id = DartLanguage.ID, name = "dart", defaultMimeType = DartLanguage.MIME_TYPE, characterMimeTypes = DartLanguage.MIME_TYPE, contextPolicy = ContextPolicy.SHARED, fileTypeDetectors = SLFileDetector.class)
@ProvidedTags({StandardTags.CallTag.class, StandardTags.StatementTag.class, StandardTags.RootTag.class, StandardTags.RootBodyTag.class, StandardTags.ExpressionTag.class, DebuggerTags.AlwaysHalt.class,
                StandardTags.ReadVariableTag.class, StandardTags.WriteVariableTag.class})
public final class DartLanguage extends TruffleLanguage<SLContext> {
    public static volatile int counter;

    public static final String ID = "dart";
    public static final String MIME_TYPE = "application/x-dart";
    private static final Source BUILTIN_SOURCE = Source.newBuilder(DartLanguage.ID, "", "Dart builtin").build();

    private final Assumption singleContext = Truffle.getRuntime().createAssumption("Single Dart context.");

    public DartLanguage() {
        System.out.println("== creo lenguaje");
        counter++;
    }

    @Override
    protected SLContext createContext(Env env) {

        System.out.println("== creo context");
        return null;
    }

    @Override
    protected boolean patchContext(SLContext context, Env newEnv) {
        System.out.println("== creo patch context");
        return true;
    }

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        Source source = request.getSource();
        Map<String, RootCallTarget> functions;
        functions = DartParser.parseDart(this, source);
        System.out.println("== parse");
        return null;
    }

    @Override
    protected void initializeMultipleContexts() {
        System.out.println("== creo context");
        singleContext.invalidate();
    }

    @Override
    protected Object getLanguageView(SLContext context, Object value) {
        System.out.println("== creo context");
        return null;
    }

    @Override
    protected boolean isVisible(SLContext context, Object value) {
        System.out.println("== creo context");
        return false;
    }

    @Override
    protected Object getScope(SLContext context) {
        System.out.println("== creo context");
        return false;
    }
}
