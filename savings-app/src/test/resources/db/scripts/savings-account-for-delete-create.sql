INSERT INTO "account" (id, user_id, name, description, account_type)
VALUES ('00000002-e89b-42d3-a456-556642440000', 'TEST_USER_1', 'bank account to delete', 'savings account description', 'SAVINGS');

INSERT INTO "savings_account" (account_id, currency, balance)
VALUES ('00000002-e89b-42d3-a456-556642440000', 'PLN', 21.37);

commit;
