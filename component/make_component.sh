declare -r JAVA_VERSION="${1:?First argument must be java version.}"
declare -r GRAALVM_VERSION="${2:?Second argument must be GraalVM version.}"

echo "33443####"
echo $GRAALVM_VERSION

if [[ $JAVA_VERSION == 1.8* ]]; then
    JRE="jre/"
else
	JRE=""
fi

readonly COMPONENT_DIR="component_temp_dir"
readonly LANGUAGE_PATH="$COMPONENT_DIR/$JRE/languages/dart"
if [[ -f ../native/dartnative ]]; then
    INCLUDE_DARTNATIVE="TRUE"
fi

rm -rf COMPONENT_DIR
mkdir -p "$LANGUAGE_PATH"
cp ../language/target/dartlanguage-*.jar "$LANGUAGE_PATH"


mkdir -p "$LANGUAGE_PATH/bin"
cp ../dart $LANGUAGE_PATH/bin/
if [[ $INCLUDE_DARTNATIVE = "TRUE" ]]; then
    cp ../native/dartnative $LANGUAGE_PATH/bin/
fi

touch "$LANGUAGE_PATH/native-image.properties"
mkdir -p "$COMPONENT_DIR/META-INF"
{
    echo "Bundle-Name: Dart Language";
    echo "Bundle-Symbolic-Name: com.uniandes.dart";
    echo "Bundle-Version: $GRAALVM_VERSION";
    echo "Bundle-RequireCapability: org.graalvm; filter:=\"(&(graalvm_version=$GRAALVM_VERSION)(os_arch=amd64))\"";
    echo "x-GraalVM-Polyglot-Part: True"
} > "$COMPONENT_DIR/META-INF/MANIFEST.MF"

(
cd $COMPONENT_DIR || exit 1
jar cfm ../dart-component.jar META-INF/MANIFEST.MF .

echo "bin/dart = ../$JRE/languages/dart/bin/dart" > META-INF/symlinks
if [[ $INCLUDE_DARTNATIVE = "TRUE" ]]; then
    echo "bin/dartnative = ../$JRE/languages/dart/bin/dartnative" >> META-INF/symlinks
fi
jar uf ../dart-component.jar META-INF/symlinks

{
    echo "$JRE"'languages/dart/bin/dart = rwxrwxr-x'
    echo "$JRE"'languages/dart/bin/dartnative = rwxrwxr-x'
} > META-INF/permissions
jar uf ../dart-component.jar META-INF/permissions
)
rm -rf $COMPONENT_DIR
