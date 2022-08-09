INSERT INTO "user_profile" (id, firstname, lastname, username)
VALUES ('USER_1', 'Paul', 'Wąsowski', 'some-test-user@gmail.com');

INSERT INTO "user_account" (user_id, main_currency)
VALUES ('USER_1', 'PLN');

INSERT INTO "account" (user_id, name, description, account_type)
VALUES ('USER_1', 'Mbank account', 'some description', 'SAVINGS');

INSERT INTO "savings_account" (account_id, currency, balance)
VALUES (1, 'USD', 527.78);
