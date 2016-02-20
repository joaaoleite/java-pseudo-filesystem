MANIFEST=META-INF/MANIFEST.MF
NOME=poof
FILES= Makefile $(MANIFEST)
all:
	javac -encoding utf-8 `find $(NOME) pt -name "*.java"`
	jar -cfm $(NOME)-`date "+%Y%m%d%H%M%S"`.jar $(MANIFEST) `find $(NOME) pt -name "*.java"` $(FILES)
	jar -cf pt.jar `find pt -name "*.class"`
	jar -cfm $(NOME).jar $(MANIFEST) pt.jar `find $(NOME) -name "*.class" -o -name "*.java"`
testes:
	chmod +x runtests.sh
	sh ./runtests.sh
run:
	java -jar $(NOME).jar
swing:
	java -Dui=swing -jar $(NOME).jar
import:
	java -Dimport=test.import poof.textui.Shell
clean:
	rm -f `find $(NOME) pt . -name "*.jar"`
	rm -f `find $(NOME) pt -name "*.class"`	