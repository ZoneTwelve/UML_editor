MAIN=UML_Editor

main:
	rm -f *.class
	#make clean
	echo "Compile program"
	javac $(MAIN).java
	echo "\e[32m---------- Program start ----------\e[0m"
	java $(MAIN)
	echo "\e[32m---------- Program end ----------\e[0m"
test:
	javac $(code).java
	java $(code)
clean:
	rm *.class