#!/bin/bash

/opt/mssql/bin/sqlservr &

# Wait for database availability
database_starting=1

while [ ${database_starting} -gt 0 ]; do
  sleep 1
  /opt/mssql-tools/bin/sqlcmd -d master -S localhost -U sa -P "${SA_PASSWORD}" -Q "select 1" &> /dev/null
  database_starting=$?
done