CREATE TABLE productType (
    prodId INT NOT NULL,
    type VARCHAR(10),
    name VARCHAR(30),
    PRIMARY KEY(prodId)
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
   balance DECIMAL,
   PRIMARY KEY (accountNumber),
   FOREIGN KEY (prodId) REFERENCES productType(prodId)
);