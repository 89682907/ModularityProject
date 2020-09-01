echo
echo [ MODULE ] \#\#\#\#\#\#\#\#\#\#\#\#\#\#\# $2
echo ../../gradlew $3:clean $3:uploadArchives --configure-on-demand -c "$4/settings_auto".gradle
echo

../../gradlew $3:clean $3:uploadArchives --configure-on-demand -c "../../settings_auto.gradle"
