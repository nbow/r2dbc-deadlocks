FROM microsoft/mssql-server-linux:latest

ENV ACCEPT_EULA="Y"
ENV SA_PASSWORD="pAASword4"
ENV DATABASE_NAME="Deadlocks"

# Install node/npm
RUN apt-get -y update

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Bundle app source
COPY ./db /usr/src/app

# Grant permissions for the import-data script to be executable
RUN chmod +x /usr/src/app/start-sql-server.sh
RUN chmod +x /usr/src/app/import-data.sh

EXPOSE 8080

CMD /bin/bash ./entrypoint.sh