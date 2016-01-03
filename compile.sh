RT_JAR=/usr/lib/jvm/java-8-openjdk/jre/lib/rt.jar
INCLUDES_DIR=../
BUKKIT_TARGET=bukkit-1.7.9B1938.jar

javac -bootclasspath "$RT_JAR:$INCLUDES_DIR/$BUKKIT_TARGET" -d ./ src/*
jar -cfe Events.jar org/c4k3/Events/Events ./*
rm -rf org/
