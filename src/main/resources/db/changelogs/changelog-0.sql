CREATE TABLE productType (
    prodId INT NOT NULL,
    type VARCHAR(10) NOT NULL,
    description VARCHAR(30) NOT NULL,
    PRIMARY KEY(prodId)
);

CREATE TABLE withdrawalStatusType (
    statusId INT NOT NULL,
    description VARCHAR(10) NOT NULL,
    PRIMARY KEY(statusId)
);


CREATE TABLE userInfo (
   id BIGSERIAL NOT NULL,
   firstName VARCHAR(40) NOT NULL,
   middleName VARCHAR(40),
   lastName VARCHAR(40) NOT NULL,

   dob DATE NOT NULL,
   cell VARCHAR(15) NOT NULL,
   email VARCHAR(40) NOT NULL,

   addressLine1 VARCHAR(20),
   addressLine2 VARCHAR(20),
   city VARCHAR(20),
   country VARCHAR(20),
   postalCode VARCHAR(20),

   PRIMARY KEY (id)
);

CREATE TABLE userAccount(
   accountNumber BIGSERIAL NOT NULL,
   prodId INT NOT NULL,
   userId BIGINT NOT NULL,
   balance DECIMAL NOT NULL,

   PRIMARY KEY (accountNumber),
   FOREIGN KEY (prodId) REFERENCES productType(prodId)
);

CREATE TABLE withdrawal (
    id BIGSERIAL NOT NULL,
    userId BIGINT NOT NULL,
    accountNumber BIGINT NOT NULL,
    status INT NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE,
    modified  TIMESTAMP WITHOUT TIME ZONE,
    amount DECIMAL NOT NULL,
    previousBalance DECIMAL NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY (accountNumber) REFERENCES userAccount(accountNumber),
    FOREIGN KEY (status) REFERENCES withdrawalStatusType(statusId)
);