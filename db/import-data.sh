#run the setup script to create the DB and the schema in the DB
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "${SA_PASSWORD}" -d master -i setup.sql

#import the data from the csv file
/opt/mssql-tools/bin/bcp Deadlocks.dbo.elements in "/usr/src/app/Elements.csv" -c -t',' -S localhost -U sa -P "${SA_PASSWORD}"
