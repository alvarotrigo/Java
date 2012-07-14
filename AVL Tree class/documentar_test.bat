set CLASSPATH=.\tests;.\lib\cacheLRU-lib-1.1.jar;.\lib\junit-4.4.jar;.\fuentes
if not exist ./docs mkdir docs
javadoc -d docs -linkoffline http://java.sun.com/j2se/1.5.0/docs/api lib fuentes\es\ubu\lsi\cacheLRU\controlador\*.java tests\es\ubu\lsi\cacheLRU\tests\*.java -author