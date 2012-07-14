if not exist .\doccheck mkdir .\doccheck
set CLASSPATH=.\fuentes;.\tests;.\lib\junit-4.5.jar
javadoc -private -doclet com.sun.tools.doclets.doccheck.DocCheck -docletpath lib\doccheck.jar -d doccheck  .\fuentes\es\ubu\lsi\util\*.java .\tests\es\ubu\lsi\util\*.java