LIB_ANTLR := lib/antlr.jar
ANTLR_SCRIPT := Micro.g4

all: group compiler
buildtest: compiler testcase
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
	java -cp lib/antlr.jar:classes/ Micro test/input/test_adv.micro > test/my_out/test_adv.out
	java -cp lib/antlr.jar:classes/ Micro test/input/test_combination.micro > test/my_out/test_combination.out
	java -cp lib/antlr.jar:classes/ Micro test/input/test_expr.micro > test/my_out/test_expr.out
	java -cp lib/antlr.jar:classes/ Micro test/input/test_if.micro > test/my_out/test_if.out
	java -cp lib/antlr.jar:classes/ Micro test/input/test_mult.micro > test/my_out/test_mult.out
	java -cp lib/antlr.jar:classes/ Micro test/input/test_while.micro > test/my_out/test_while.out
	java -cp lib/antlr.jar:classes/ Micro test/input/estimatePi.micro > test/my_out/estimatePi.out
	java -cp lib/antlr.jar:classes/ Micro test/input/estimatePi.micro > test/my_out/estimatePi.out
	java -cp lib/antlr.jar:classes/ Micro test/input/bitstream.micro > test/my_out/bitstream.out
	java -cp lib/antlr.jar:classes/ Micro test/input/quicksort.micro > test/my_out/quicksort.out
	java -cp lib/antlr.jar:classes/ Micro test/input/integral.micro > test/my_out/integral.out
clean:
	rm -rf classes build

.PHONY: all group compiler clean
