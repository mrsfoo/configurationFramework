configurationFramework
======================

configuration framework, written in java, very incomplete and in working state, feel free to clone anyways;
state: in progress and buggy!


add as eclipse project
======================
1. checkout into <topfolder>/configurationFramework
2. set eclipse workspace to <topfolder>
3. create new java project > project name "configurationFramework"
4. import JUnit3 library; this is done easiest if you navigate to one of the red x'es in sourcecode and click on it
5. add all JARs in the libs folder to build path; this is done easiest by right-clicking in them in the package explorer and "add to build path"
6. for logging (not aplicable for this project in the moment):
   a. add the following log4j libs to the build path:
      - log4j-core-2.0-beta9.jar
      - log4j-api-2.0-beta9.jar
   b. add the folder where a logging configuration file log4j2.xml is located to the class path; in eclipse this can be done by adding the folder as source folder
   c. you can checkout logging libs and configuration from https://github.com/mrsfoo/mylog4j.git
