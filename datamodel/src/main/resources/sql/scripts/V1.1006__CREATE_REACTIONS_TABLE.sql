CREATE TABLE REACTIONS_T01
(
    OID        BIGINT UNSIGNED PRIMARY KEY,
    UserOID    BIGINT UNSIGNED,
    ServerOID  BIGINT UNSIGNED,
    MessageOID BIGINT UNSIGNED,
    ReactionId BIGINT UNSIGNED,
    RoleId     BIGINT UNSIGNED
);