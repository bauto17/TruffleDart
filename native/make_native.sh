#!/usr/bin/env bash

if [[ $DART_BUILD_NATIVE == "false" ]]; then
    echo "Skipping the native image build because DART_BUILD_NATIVE is set to false."
    exit 0
fi

/mnt/c/Users/luisb/tesis/graalvm-ce-java11-22.0.0.2/bin/native-image \
    --macro:truffle --no-fallback --initialize-at-build-time \
    -cp ../language/target/dartlanguage-22.0.0.2.jar:../launcher/target/dart-launcher.jar \
    edu.uniandes.dart.launcher.DartMain \
    dartnative