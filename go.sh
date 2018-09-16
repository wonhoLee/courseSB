cd courseSB
git pull
~/apache-tomcat-8.5.34/bin/shutdown.sh
./mvnw clean package
cd target
rm -rf ~/apache-tomcat-8.5.34/webapps/ROOT/
mv courseSB-1.0/ ~/apache-tomcat-8.5.34/webapps/ROOT/
~/apache-tomcat-8.5.34/bin/startup.sh
tail -500f ~/apache-tomcat-8.5.34/logs/catalina.out
