@echo off
call ../../gradlew uploadModularityTask -b ../../gradle/upload.gradle -P projectType=modularity
