#!/bin/bash
clear
mkdir bin
echo "compiling"
javac -d ./bin/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls ./src/*.java
echo "" &&
mkdir doc
echo "doc" &&
javadoc -d ./doc/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls ./src/*.java &&
echo "" &&
echo "running" &&
echo "" &&
java -cp ./bin/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls Pendu