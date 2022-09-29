# Viya4 Automation Test

This project has selenium based validation test. This will validate the Viya 4 deployment quickly.

## Executing the Project in Windows OS
1. **Install Java 11+**
    - To install Java, you first need to download the installer program from Oracle. 
    - Visit the Download Java page: https://www.oracle.com/technetwork/java/javase/downloads/index.html. 
    - Click on Download button.
    - Once downloaded start the setup process and follow the steps.
    - Set Environment Variables for - The Path Variable gives the location of executables like javac, java, etc. JDK's programs (such as Java compiler javac.exe and Java runtime java.exe) reside in the sub-directory bin of the JDK installed directory. You need to include JDK's bin in the path to run the JDK programs.
    - Once completed open command prompt and test below command to see if java is installed
    ```
    java -version
    ```


2. **Setup Maven 3.8.1**
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
4. Clone this project and you should see the "viya4-automation-test" folder
    ```
    git clone https://gitlab.sas.com/sinbrv/viya4-automation-test
    cd viya4-automation-test
    ```
5. Need to Add Dependency (Selenium and TestNG) and Plugins to Maven Project for Selenium and TestNG. In pom.xml already dependency is added.
6. Modify TESTNG XML as per environment. TestNG XML can be found /viya4-automation-test/src/test/resources/SASValidationTest.xml The Major parameter to validate are as below
    - username - Username used for the viya4 application
    - password - Password used for the viya4 application
    - headLess - wether you want GUI or NonGUI
    - sampleDataPath - csv file to be imported for Visual Analytics 
    - sampleDemographicDataPath - csv file to be imported for Visual Analytics
    - url - SAS Viya 4 url to be tested. url you will found in each test case so replace It
    - Also test which you want to execute please make note of that too.

7. Execute TestNG XML to run test. In Eclipse select SASValidationTest.xml and Righ click > Run AS > Select TestNG Suite




## Executing the Project in Ubuntu
Same selenium project can also be executed in Ubuntu or any other Linux OS.

1. Install Chromium
```
   sudo apt-get install -y libappindicator1 fonts-liberation
   sudo apt-get install -f
   sudo apt install -y unzip xvfb libxi6 libgconf-2-4
   wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
   sudo dpkg -i google-chrome*.deb
   sudo curl -sS -o - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add
   sudo bash -c "echo 'deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main' >> /etc/apt/sources.list.d/google-chrome.list"
   sudo apt -y update
   sudo apt -y install google-chrome-stable
   sudo apt --fix-broken install
   sudo apt -y install google-chrome-stable
   google-chrome --version
   ```

2. Install Java 11+
Execute the following command to install the default Java Runtime Environment (JRE), which will install the JRE from OpenJDK 11
```
sudo apt install default-jre
```
3. Setup Maven 3.8.1
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
4. Clone the project, Modify TESTNG XML as per environment. In certain cases modify the pom.xml based on maven version and java version in linux.
5. Execute TestNG XML using below command
```
mvn test -DsuiteXmlFile=/src/test/resources/SASValidationTest.xml
```

## Executing the Project in Docker
The selenium automation test can also be executed as docker command
1. Install Docker in the Linux environment. 
2. Clone the project and go to the solution
3. Modify TESTNG XML as per environment in the cloned project.
4. Build Docker Image
```
docker image build -t viyavalidationtest .
```
5. Execute Docker Command using TestNG XML
```
docker run viyavalidationtest mvn test -DsuiteXmlFile=/src/test/resources/SASValidationTest.xml
```
6. Copy the report from the container as below
```
docker cp <containerId>:/AllTests.html /home/ubuntu/reports/AllTests.html
docker cp <containerId>:/FailedTests.html /home/ubuntu/reports/AllTests.html
docker cp <containerId>:/Screenshots /home/ubuntu/reports/Screenshots
```
