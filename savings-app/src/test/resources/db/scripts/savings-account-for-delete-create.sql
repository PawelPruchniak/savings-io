INSERT INTO "account" (user_id, name, description, account_type)
VALUES ('TEST_USER_1', 'bank account to delete', 'savings account description', 'SAVINGS');

INSERT INTO "savings_account" (account_id, currency, balance)
VALUES (2, 'PLN', 21.37);

commit;
