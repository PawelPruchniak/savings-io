INSERT INTO "account" (id, user_id, name, description, account_type)
VALUES ('00000005-e89b-42d3-a456-556642440000', 'TEST_USER_1', 'Investment account - GPW', 'investment account description', 'INVESTMENT');

INSERT INTO "investment_account" (account_id, asset, asset_quantity, currency_invested, amount_invested)
VALUES ('00000005-e89b-42d3-a456-556642440000', 'GPW', '20', 'PLN', 626.25);

commit;
