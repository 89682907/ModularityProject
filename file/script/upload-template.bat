@echo off
echo ############-%2
echo "%~dp0..\..\gradlew" %3:clean %3:uploadArchives --configure-on-demand -c "%~4/settings_auto_build".gradle

"%~dp0..\..\gradlew" %3:clean %3:uploadArchives --configure-on-demand -c "%~4/settings_auto_build.gradle"