# Java Makefile

# JAVA flags
JFLAGS = -g
JVM = java
JC = javac

# directories
JCLASSDIR = bin
JSOURCEDIR = src

# compiling the class
%.class: %.java
	$(JC) $(JFLAGS) $<

# set sources
SRCS := $(wildcard $(JSOURCEDIR)/*.java)

# set classes
CLS := $(wildcard $(JCLASSDIR)/*.class)

# location of main functions
MAIN = Main

# rules
default: release

# `make compile`
compile:
	mkdir -p $(JCLASSDIR)
	$(JC) -d ./$(JCLASSDIR) $(JFLAGS) $(SRCS)

# `make release`
release:
	mkdir -p $(JCLASSDIR)
	$(JC) -d ./$(JCLASSDIR) $(JFLAGS) $(SRCS)
	$(JVM) -cp ./$(JCLASSDIR) $(MAIN)

# `make clean`
.PHONY: clean
clean:
	rm -rf $(JCLASSDIR)
