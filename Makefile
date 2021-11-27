all: compile run

run:
	java Main

compile: 
	javac -d . graph_core/*.java
	javac -d . graph_representations/*.java
	javac -d . graph_searches/*.java
	javac -d . ford_fulkerson/*.java
	javac Main.java

clean: 
	rm -rf aboutGraphs