INSERT INTO "user_profile" (id, firstname, lastname, username, password, enabled)
VALUES ('USER_1', 'Paul', 'Wąsowski', 'some-test-user@gmail.com', 'passowrd', 'true');

INSERT INTO "user_authorities" (username, authority)
VALUES ('some-test-user@gmail.com', 'USER_ROLE');

INSERT INTO "user_account" (user_id, main_currency)
VALUES ('USER_1', 'PLN');

/* Savings account */
INSERT INTO "account" (id, user_id, name, description, account_type)
VALUES ('00000001-e89b-42d3-a456-556642440000', 'USER_1', 'Mbank account', 'some description', 'SAVINGS');

INSERT INTO "savings_account" (account_id, currency, balance)
VALUES ('00000001-e89b-42d3-a456-556642440000', 'USD', 527.78);

/* Investment account */
INSERT INTO "account" (id, user_id, name, description, account_type)
VALUES ('00000002-e89b-42d3-a456-556642440000', 'USER_1', 'GPW investment account', 'some description', 'INVESTMENT');

INSERT INTO "investment_account" (account_id, asset, asset_quantity, currency_invested, amount_invested)
VALUES ('00000002-e89b-42d3-a456-556642440000', 'GPW', '20', 'PLN', '612.12')
