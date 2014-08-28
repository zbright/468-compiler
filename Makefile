all: group compiler

group:
	@echo "Zach Bright (zbright)"
	@echo "Nathan Tornquist (ntornqui)"

compiler: helloworld

helloworld: helloworld.c
	$(CC) -o $@ $<

clean:
	rm helloworld
