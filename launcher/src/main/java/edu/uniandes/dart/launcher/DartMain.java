package edu.uniandes.dart.launcher;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DartMain {
    private static final String EXTENSION = "dart";

    public static void main(String[] args) throws IOException {
        Source source;
        String file = null;
        for (String arg : args) {
            if (!(arg.length() <= 2 || !arg.startsWith("--"))) {
                continue;
            } else {
                if (file == null) {
                    file = arg;
                }
            }
        }

        if (file == null) {
            source = Source.newBuilder(EXTENSION, new InputStreamReader(System.in), "<stdin>").build();
        } else {
            source = Source.newBuilder(EXTENSION, new File(file)).build();
        }

        System.exit(executeSource(source, System.in, System.out, Collections.emptyMap()));
    }

    private static int executeSource(Source source, InputStream in, PrintStream out, Map<String, String> options) {
        Context context;
        PrintStream err = System.err;
        try {
            context = Context.newBuilder(EXTENSION).in(in).out(out).options(options).build();
        } catch (IllegalArgumentException e) {
            err.println(e.getMessage());
            return 1;
        }
        out.println("== running on " + context.getEngine());
        out.println("== running on " + context.getBindings(EXTENSION));

        try {
            Value result = context.eval(source);
            if (context.getBindings(EXTENSION).getMember("main") == null) {
                err.println("No function main() defined in SL source file.");
                return 1;
            }
            if (!result.isNull()) {
                out.println(result.toString());
            }
            return 0;
        } catch (PolyglotException ex) {
            if (ex.isInternalError()) {
                // for internal errors we print the full stack trace
                ex.printStackTrace();
            } else {
                err.println(ex.getMessage());
            }
            return 1;
        } finally {
            context.close();
        }
    }
}
