INSERT INTO userInfo (firstName, middleName, lastName, cell, email,
                    addressLine1, addressLine2, city, country, postalCode,
                    accountNumber)
                     VALUES ('Tom', 'Timothy', 'Hardy', '0123456789', 'email.@address.com',
                     '123 Something Street', 'Building 1', 'Newhannesburg', 'North Africa', '6212',
                     '{"121234345656", "787890901111"}');

INSERT INTO productType (prodId, type, name) VALUES (0, 'RETIREMENT', 'OMNI Retirement Account');
INSERT INTO productType (prodId, type, name) VALUES (1, 'SAVINGS', 'True Savings Account');

INSERT INTO userAccount (accountNumber, prodId, balance) VALUES ('121234345656', 0, 500000);
INSERT INTO userAccount (accountNumber, prodId, balance) VALUES ('787890901111', 1, 36000);
