LIB_ANTLR := lib/antlr.jar
ANTLR_SCRIPT := Micro.g4

all: group compiler

group:
	@echo "zbright ntornqui"
compiler:
	rm -rf build
	mkdir build
	java -cp $(LIB_ANTLR) org.antlr.v4.Tool -o build $(ANTLR_SCRIPT)
	rm -rf classes
	mkdir classes
	javac -cp $(LIB_ANTLR) -d classes src/*.java build/*.java
testcase:
	java -cp lib/antlr.jar:classes/ Micro test/input/fma.micro > test/my_out/fma.out
	java -cp lib/antlr.jar:classes/ Micro test/input/fibonacci2.micro > test/my_out/fibonacci2.out
	java -cp lib/antlr.jar:classes/ Micro test/input/factorial2.micro > test/my_out/factorial2.out
clean:
	rm -rf classes build

.PHONY: all group compiler clean
