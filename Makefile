all: group compiler

group:
	@echo "Zach Bright and Nathan Tornquist"

compiler: helloworld

helloworld: helloworld.c
	$(CC) -o $@ $<

clean:
	rm helloworld
