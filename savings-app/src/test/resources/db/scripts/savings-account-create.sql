INSERT INTO "account" (user_id, name, description, account_type)
VALUES ('TEST_USER_1', 'first bank account', 'savings account description', 'SAVINGS');

INSERT INTO "savings_account" (account_id, currency, balance)
VALUES (1, 'USD', 527.78);

commit;
