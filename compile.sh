shopt -s globstar
rm -rf bin/**/*
javac -cp ./lib/gson-2.5.jar -d bin **/*.java
