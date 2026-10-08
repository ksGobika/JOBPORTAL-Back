@echo off
set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
"%DIRNAME%tools\apache-maven-3.9.6\bin\mvn.cmd" %*
