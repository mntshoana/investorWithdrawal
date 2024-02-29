# Investors
This project was a task assinged to me with a focous towards creating an api for an investment withdrawal problem-case. Hence, it is only a dummy project for assessing my knowledge in creating an Rest APIs using springboot


## Requirements
The project requires that you have jdk 11 as this is what I used to develop the program. You may try anything above 11.
The project also requires that you have postgreSQL installed. Make sure that the bin folder is in your path variables

You should also have maven installed to run mvn commands.


## Usage
Clone and compile using

````
git@github.com:mntshoana/investorWithdrawal.git
cd investorWithdrawal
mvn clean install
````

NOTE (WINDOWS USERS)
Make sure to manually run .\postgres.bat from a 'cmd" terminal (not powershell). It has been excluded from the antrun script
````
cmd start /min cmd.exe /c .\postgres.bat
````


Following this (MAC, LINUX AND WINDOWS), run the app using
````
mvn spring-boot:run -Dspring-boot.run.profiles=dev
````

You may now head to http://127.0.0.1:8443/swagger-ui/index.html to test the api from swagger

## Once done
remember to shut down postgres server by running
````
pg_ctl stop -D ./src/main/resources/db/investorWithdrawal
````
(on windows)
````
pg_ctl stop -D .\src\main\resources\db\investorWithdrawal
````

You may also delete the database files by running (on linux/mac)
````
rm -rfd ./src/main/resources/db/investorWithdrawal
````
(on windows)
````
rmdir .\src\main\resources\db\investorWithdrawal
````