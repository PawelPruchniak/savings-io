INSERT INTO "user_profile" (id, firstname, lastname, username, password, enabled)
VALUES ('TEST_USER_1', 'Alex', 'Smith', 'alex.smith', 'alex.smith.password', 'true');

INSERT INTO "user_authorities" (username, authority)
VALUES ('alex.smith', 'USER_ROLE');

commit;
