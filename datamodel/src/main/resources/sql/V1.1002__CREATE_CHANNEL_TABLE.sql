CREATE TABLE CHANNEL_T01
(
    OID         BIGINT PRIMARY KEY,
    ServerOID   BIGINT ,
    Channelname VARCHAR(30),
    CONSTRAINT FK_ServerOID FOREIGN KEY (ServerOID) REFERENCES SERVER_T01 (OID)
);