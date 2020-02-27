#!/bin/bash

echo "Starting SQL server..."
/usr/src/app/start-sql-server.sh

echo "Schema run .............."
/usr/src/app/import-data.sh

echo "Sleeping...."
sleep infinity