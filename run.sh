clear
# javac -d ./bin/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls ./src/*.java &&
javac -d ./bin/ --module-path /home/glados/Downloads/javafx-sdk-22.0.1/lib/ --add-modules javafx.controls ./src/*.java &&
# java  -cp ./bin/:./graphics/ --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls Pendu
java  -cp ./bin/:./graphics/ --module-path /home/glados/Downloads/javafx-sdk-22.0.1/lib/ --add-modules javafx.controls Pendu