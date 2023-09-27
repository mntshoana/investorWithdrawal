# Investors
This project was a task assinged to me with a focous towards creating an api for an investment withdrawal problem-case. Hence, it is only a dummy project for assessing my knowledge in creating an Rest APIs using springboot


## Requirements
The project requires that you have jdk 11 installed on your computer as well as maven.

## Usage
Compile using

````
git@github.com:mntshoana/investorWithdrawal.git
cd investorWithdrawal
mvn clean install
````
then 
````
mvn spring-boot:run
````

## Once done
remember to shut down postgres server by running
````
pg_ctl stop -D ./src/main/resources/db/investorWithdrawal
````

You may also delete the database files by running (on linux/mac)
````
rm -rfd ./src/main/resources/db/investorWithdrawal
````
