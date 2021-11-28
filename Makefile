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
<<<<<<< HEAD
	rm -rf AboutGraphs
	rm *.class
=======
	rm -rf AboutGraphs
>>>>>>> 1b387950559dd2ef737390340f12853d43947502
