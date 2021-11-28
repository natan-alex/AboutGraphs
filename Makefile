all: compile run

run:
	java Main

compile: 
	javac -d . core/*.java
	javac -d . representations/*.java
	javac -d . searches/*.java
	javac -d . fordFulkerson/*.java
	javac Main.java

clean: 
	rm -rf AboutGraphs
	rm *.class
