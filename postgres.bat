@echo off

GOTO:main

:createDatabaseIfNotExist
    timeout /t 3
    echo "Checking if database investorWithdrawal exists..."
    psql -U postgres -c "SELECT datname FROM pg_database;" > result 2>&1
    type result | findstr /c:"investorwithdrawal" && (
        echo "Already exists! Nothing left to do."
    ) || (
        echo "Executing CREATE DATABASE investorWithdrawal"
        psql -U postgres -c "CREATE DATABASE investorWithdrawal;" > result 2>&1
        echo "DONE creating database"
    )
EXIT /B 0

:runServerIfNotUp
    pg_ctl status -D .\src\main\resources\db\investorWithdrawal > result 2>&1
    type result | findstr /c:"no server running" && (
        echo "Running server investorWithdrawal"
        start "" /B  pg_ctl start -D .\src\main\resources\db\investorWithdrawal -s -w -l.\src\main\resources\db\investorWithdrawal\errlog
        echo "DONE running server"
    )
EXIT /B 0

:createIfNotExist
    pg_ctl status -D .\src\main\resources\db\investorWithdrawal  > result 2>&1
        type result | findstr /c:"not exist" && (
        echo "Executing initdb investorWithdrawall"
        initdb -D .\src\main\resources\db\investorWithdrawal --username=postgres --no-locale --encoding=UTF8 > result 2>&1
        echo "DONE"
  )
EXIT /B 0

:main
psql --version | findstr /c:"psql \(PostgreSQL\)" && (
    CALL :createIfNotExist
    CALL :runServerIfNotUp
    CALL :createDatabaseIfNotExist
    del result
    timeout /nobreak 3 >nul 2>nul
) || (
    echo "You don't have PostgreSQL installed."
    echo "Please install it and try running this command again."
    exit /B -1
)
