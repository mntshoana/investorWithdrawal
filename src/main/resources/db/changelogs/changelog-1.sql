INSERT INTO userInfo (firstName, middleName, lastName, dob, cell, email,
                    addressLine1, addressLine2, city, country, postalCode)
                     VALUES ('Tom', 'Timothy', 'Hardy', '1990-01-02', '0123456789', 'email.@address.com',
                     '123 Something Street', 'Building 1', 'Newhannesburg', 'North Africa', '6212');
INSERT INTO userInfo (firstName, middleName, lastName, dob, cell, email,
                    addressLine1, addressLine2, city, country, postalCode)
                     VALUES ('Sarah', 'Janoury', 'Lesuka', '1980-07-21', '0122109869', 'sarah.@address.com',
                     '123 Somewhere Drive', 'Block 5', 'Old Town', 'Southernland', '0093');

INSERT INTO userInfo (firstName, lastName, dob, cell, email,
                    addressLine1, city, country, postalCode)
                     VALUES ('Lebohang', 'Sethole', '1999-11-10', '0519995454', 'lebo.sethole@yahee.com',
                     '27 Church Street', 'Fluerdale', 'Amininka', '777034');

INSERT INTO productType (prodId, type, description) VALUES (0, 'RETIREMENT', 'OMNI Retirement Account');
INSERT INTO productType (prodId, type, description) VALUES (1, 'SAVINGS', 'True Savings Account');

INSERT INTO withdrawalStatusType (statusId, description) VALUES (0, 'STARTED');
INSERT INTO withdrawalStatusType (statusId, description) VALUES (1, 'EXECUTING');
INSERT INTO withdrawalStatusType (statusId, description) VALUES (2, 'DONE');
INSERT INTO withdrawalStatusType (statusId, description) VALUES (3, 'FAILED');