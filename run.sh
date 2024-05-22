clear
javac -d ./bin/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls ./src/*.java &&
java  -cp ./bin/:./graphics/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls Pendu