#!/bin/bash

createDatabaseIfNotExist(){
  sleep 3
  echo "Checking if database investorWithdrawal exists..."
  psql -U postgres \
     -c "SELECT datname FROM pg_database;" &> result

  if [[ `egrep -c 'investorwithdrawal' result` -le 0 ]]
  then
    echo "Executing CREATE DATABASE investorWithdrawal"
    statement=
    psql -U postgres \
    -c 'CREATE DATABASE investorWithdrawal;' &> result
    echo "DONE creating database"
  else
      echo "Already exists! Nothing left to do."
  fi
}

runServerIfNotUp(){
  pg_ctl status -D ./src/main/resources/db/investorWithdrawal &> result
  if [[ `egrep -c 'no server running' result` -ge 1 ]]
  then
    echo "Running server investorWithdrawal"
    pg_ctl start -D ./src/main/resources/db/investorWithdrawal >/dev/null &
    echo "DONE running server"
  fi
}

createIfNotExist(){
  pg_ctl status -D ./src/main/resources/db/investorWithdrawal &> result
  if [[ `egrep -c 'not exist' result` -ge 1 ]]
  then
    echo "Executing initdb investorWithdrawal"
    initdb -D ./src/main/resources/db/investorWithdrawal --username=postgres --no-locale --encoding=UTF8 &> result
    echo "DONE"
  fi
}

if [[ `psql --version | egrep -c 'psql \(PostgreSQL\)'` -ge 1 ]];
then
  createIfNotExist
  runServerIfNotUp
  createDatabaseIfNotExist
  rm result
else
  echo "You don't have PostgreSQL installed."
  echo "Please install it and try running this command again."
  exit -1
fi
