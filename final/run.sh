echo 'running airport_routes.py';
echo '---------- ----------\n';

python3 airport_routes.py

echo '\nrunning AirportRoutes.java';
echo '---------- ----------\n';

javac *.java;
java -Xss100m AirportRoutes.java
rm *.class;
