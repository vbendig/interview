CREATE DATABASE IF NOT EXISTS movies_db;
CREATE USER IF NOT EXISTS 'maverickAdmin'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON maverick_db.* TO 'maverickAdmin'@'%';

CREATE USER IF NOT EXISTS 'maverickUser'@'%' IDENTIFIED BY 'password';
GRANT CREATE, SELECT, INSERT, UPDATE, DELETE ON maverick_db.* TO 'maverickUser'@'%';

CREATE USER IF NOT EXISTS 'flyway'@'%' IDENTIFIED BY 'falafly';
CREATE USER IF NOT EXISTS 'flywayDEL'@'%' IDENTIFIED BY 'falafly';

GRANT DELETE, DROP ON maverick_db.* TO 'flywayDEL'@'%';
GRANT CREATE, SELECT, INSERT, UPDATE, ALTER, REFERENCES ON maverick_db.* TO 'flyway'@'%';
FLUSH PRIVILEGES;