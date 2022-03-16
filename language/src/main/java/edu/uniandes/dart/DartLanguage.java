package edu.uniandes.dart;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;

@TruffleLanguage.Registration(id = DartLanguage.ID, name = "DART", defaultMimeType = DartLanguage.MIME_TYPE, characterMimeTypes = DartLanguage.MIME_TYPE, contextPolicy = TruffleLanguage.ContextPolicy.SHARED, fileTypeDetectors = DartFileDetector.class)
@ProvidedTags({StandardTags.CallTag.class, StandardTags.StatementTag.class, StandardTags.RootTag.class, StandardTags.RootBodyTag.class, StandardTags.ExpressionTag.class, DebuggerTags.AlwaysHalt.class,
        StandardTags.ReadVariableTag.class, StandardTags.WriteVariableTag.class})
public final class DartLanguage extends TruffleLanguage<OwnContex> {

    public static final String ID = "dart";
    public static final String MIME_TYPE = "application/x-dart";

    @Override
    protected OwnContex createContext(Env env) {
        System.out.println("creo el contexto en el lenguaje.");
        return null;
    }

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        System.out.println("entro al parser");
        return super.parse(request);
    }
}

final class OwnContex {

}