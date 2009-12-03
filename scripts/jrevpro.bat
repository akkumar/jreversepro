@echo off

IF "%JREVERSE_HOME%" == "" goto envpath

set CP=%JREVERSE_HOME%\lib\jreversepro.jar
 
java -classpath %CP% jreversepro.JCmdMain %1 %2 %3 %4 %5
goto end

:envpath
echo Set JREVERSE_HOME environment variable.

:end
set CP=
