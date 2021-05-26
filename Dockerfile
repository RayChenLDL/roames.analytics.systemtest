FROM openjdk:8-jre-alpine

# Add the jar with all the dependencies
# Maven creates RoamesDataGridsTestAutomation.jar in the target folder of my workspace.
# We need this in some location - say - /usr/share/tag folder of the container
ADD target/RoamesDataGridsTestAutomation.jar /usr/share/tag/RoamesDataGridsTestAutomation.jar

ADD target/libs /usr/share/tag/libs

ADD src/test/resources/executables /src/test/resources/executables
ADD src/test/resources/extentconfig /src/test/resources/extentconfig
ADD src/test/resources/logs /src/test/resources/logs
ADD src/test/resources/properties /src/test/resources/properties
ADD src/test/resources/runner /src/test/resources/runner
ADD src/test/resources/testdata/excel /src/test/resources/testdata/excel
ADD src/test/resources/testdata/json /src/test/resources/testdata/json
ADD src/test/resources/FUGRO_Logo.jpg /src/test/resources/FUGRO_Logo.jpg

# Command line to execute the test
#ENTRYPOINT ["/usr/bin/java", "-cp", "/usr/share/tag/RoamesTestAutomation.jar:/usr/share/tag/libs/*", "org.testng.TestNG", "/src/test/resources/runner/$MODULE.xml"]
ENTRYPOINT /usr/bin/java -cp /usr/share/tag/RoamesDataGridsTestAutomation.jar:/usr/share/tag/libs/*:/src/test/resources/properties -Dpassphrase=$passphrase -DEnvironment=$TESTENV -DParallelThreads=$ParallelThreads -DHUB_HOST=$HUB_HOST -DSELENIUM_GRID=$SELENIUM_GRID -Dbrowser=$browser org.testng.TestNG /src/test/resources/runner/$MODULE.xml