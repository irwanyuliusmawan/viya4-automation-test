# Viya4 Automation Test

This project has selenium based validation test. This will validate the Viya 4 deployment quickly.

## Executing the Project in Windows OS
1. Install Java 11+
- To install Java, you first need to download the installer program from Oracle. 
- Visit the Download Java page: https://www.oracle.com/technetwork/java/javase/downloads/index.html. 
- Click on Download button.
- Once downloaded start the setup process and follow the steps.
- Set Environment Variables for - The Path Variable gives the location of executables like javac, java, etc. JDK's programs (such as Java compiler javac.exe and Java runtime java.exe) reside in the sub-directory bin of the JDK installed directory. You need to include JDK's bin in the path to run the JDK programs.
- Once completed open command prompt and test below command to see if java is installed
```
java -version
```


2. Setup Maven 3.8.1
- The next step is to download the Maven and it can be downloaded from this https://archive.apache.org/dist/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz 
- Extract it to some location. It can be extracted it to 'C:/apache-maven-3.8.1'
- Set up the Maven Environment Variable the same way we set up the Java Environment Variable above. Write 'MAVEN_HOME' in the Variable name box then enter 'C:\apache-maven-3.8.1*' Maven path in the Variable value box and click OK.
- To run Maven from the command prompt, this is necessary to update the Path Variable with Maven’s installation 'bin' directory in Environment Variable.
- Test from command prompt if maven is working
```
mvn -version
```

3. Install Eclipse IDE Latest
Download and Install Eclipse IDE from below link
https://www.eclipse.org/downloads/packages/installer
4. To Create Maven Project in Eclipse IDE
5. Need to Add Dependency (Selenium and TestNG) and Plugins to Maven Project for Selenium and TestNG
6. Modify TESTNG XML as per environment
6. Execute TestNG XML


## Executing the Project in Ubuntu
1. Install Java 11+
Execute the following command to install the default Java Runtime Environment (JRE), which will install the JRE from OpenJDK 11
```
sudo apt install default-jre
```
2. Setup Maven 3.8.1
Download the Apache Maven in the /tmp directory:
```
wget https://archive.apache.org/dist/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz -P /tmp
```
Once the download is completed, extract the archive in the /opt directory
```
sudo tar xf /tmp/apache-maven-*.tar.gz -C /opt
```
Setup symbolic link
```
sudo ln -s /opt/apache-maven-3.8.1 /opt/maven
```
Next, we’ll need to set up the environment variables. To do so, open your text editor and create a new file named maven.sh in the /etc/profile.d/ directory.
```
sudo nano /etc/profile.d/maven.sh
```
Paste following code
```
export JAVA_HOME=/usr/lib/jvm/default-java
export M2_HOME=/opt/maven
export MAVEN_HOME=/opt/maven
export PATH=${M2_HOME}/bin:${PATH}
```
Save and close the file. This script will be sourced at shell startup.

Make the script executable with chmod
```
sudo chmod +x /etc/profile.d/maven.sh
```
Finally, load the environment variables using the source command
```
source /etc/profile.d/maven.sh
```
Test the maven is working
```
mvn -version
```
3. Modify TESTNG XML as per environment
4. Execute TestNG XML

## Executing the Project in Docker
1. Install Docker
2. Build Docker Image
3. Modify TESTNG XML as per environment
4. Execute Docker Command using TestNG XML
