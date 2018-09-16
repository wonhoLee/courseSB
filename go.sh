cd courseSB
echo "Welcome My Shell"
TOMCAT_HOME=~/apache-tomcat-8.5.34
git pull
$TOMCAT_HOME/bin/shutdown.sh
./mvnw clean package
cd target
rm -rf $TOMCAT_HOME/webapps/ROOT/
mv courseSB-1.0/ $TOMCAT_HOME/webapps/ROOT/
$TOMCAT_HOME/bin/startup.sh
tail -500f $TOMCAT_HOME/logs/catalina.out
