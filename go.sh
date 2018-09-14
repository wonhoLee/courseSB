cd coursesb
git pull
./mvnw clean package
~/apache-tomcat-8.5.34/bin/shutdown.sh
cd target
mv coursesb-1.0 ~/apache-tomcat-8.5.34/webapps/ROOT
~/apache-tomcat-8.5.34/bin/startup.sh
