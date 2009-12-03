@echo off

IF "%JREVERSE_HOME%" == "" goto envpath

set CP=%JREVERSE_HOME%\jreversepro.jar;%JREVERSE_HOME%\lib\commons-cli-1.2.jar
 
java -classpath %CP% net.sf.jrevpro.cmd.CommandMain %1 %2 %3 %4 %5
goto end

:envpath
echo Set JREVERSE_HOME environment variable.

:end
set CP=
