-- "Remember Me" de Spring Security
CREATE TABLE IF NOT EXISTS persistent_logins (username VARCHAR(64) NOT NULL, series VARCHAR(64) PRIMARY KEY, token VARCHAR(64) NOT NULL, last_used TIMESTAMP NOT NULL);

-- Necesario crear también esta tabla manualmente (y sus correspondientes inserts) en la base de datos de OpenKM a través de la utilidad del administrador database query,
-- en el caso de que la base de datos apunte a otra externa que no sea la propia de OpenKM
-- DROP TABLE INV_DOCUMENT_TYPE;
-- CREATE TABLE INV_DOCUMENT_TYPE(DTP_ID INT NOT NULL, DTP_TYPE VARCHAR(100) NOT NULL, DTP_METADATA_GROUP VARCHAR(100) NOT NULL);

INSERT INTO INV_DOCUMENT_TYPE (DTP_ID, DTP_TYPE, DTP_METADATA_GROUP) VALUES (1, 'invoice', 'okg:invoice');
INSERT INTO INV_DOCUMENT_TYPE (DTP_ID, DTP_TYPE, DTP_METADATA_GROUP) VALUES (2, 'deliverynote', 'okg:delivery_note');

INSERT INTO INV_USER (USR_ID, USR_NAME, USR_PASSWORD, USR_ROLE) VALUES ('admin', 'Administrator', '$2a$10$B/EEVLP4g/7UWGcM6ltZX.Ko7dZfb33Yyj.pxNXTaKrMiapT1KM8m', 'ROLE_ADMIN');
INSERT INTO INV_ROLE (ROL_ID) VALUES ('ROLE_STANDARD');
INSERT INTO INV_ROLE (ROL_ID) VALUES ('ROLE_ADVANCED');
INSERT INTO INV_ROLE (ROL_ID) VALUES ('ROLE_ADMIN');
