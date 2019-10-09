FROM proxysql/proxysql:latest

COPY proxysql.cnf /etc/proxysql.cnf

RUN apt update && \
    apt install mysql-client -y
